#!/usr/bin/python
import argparse
import shutil
import ssl
import tempfile
import urllib
import urllib.request
from xml.etree import ElementTree
import os.path
import sys
import errno
from os.path import expanduser
import zipfile

COLOR_RED = '\033[1;31m'
COLOR_YELLOW = '\033[1;33m'
COLOR_BLUE = '\033[1;34m'
COLOR_CYAN = '\033[1;36m'
END_COLOR = '\033[1;m'

UPDATE_SITE_SIZE = 2.6 * 1024 * 1024 * 1024

TALEND_MAVEN_PLUGINS = [
    'org.talend.ci:builder-maven-plugin',
    'org.talend.ci:signer-maven-plugin',
    'org.talend.ci:cloudpublisher-maven-plugin'
]

NAMESPACE = '{http://maven.apache.org/POM/4.0.0}'
VERSION = NAMESPACE + 'parent' + '/' + NAMESPACE + 'version'


def install_opener(nexus_domain, login, password, verify_certificate=False):
    """
    install authentication manager for specific user/password on specific domain
    :param nexus_domain: the nexus server containing the plugin
    :param login: user login
    :param password: user password
    :param verify_certificate: should we verify SSL certificate (False as auto generated)
    :return:
    """
    auth_handler = None
    print("installing authentication for user %s" % login)
    context = ssl.SSLContext(ssl.PROTOCOL_TLSv1_2)
    if verify_certificate:
        context.verify_mode = ssl.CERT_REQUIRED
    https_handler = urllib.request.HTTPSHandler(context=context)
    if login is not None and password is not None:
        manager = urllib.request.HTTPPasswordMgrWithDefaultRealm()
        manager.add_password(None, nexus_domain, login, password)
        auth_handler = urllib.request.HTTPBasicAuthHandler(manager)
    if https_handler is not None:
        if auth_handler is not None:
            opener = urllib.request.build_opener(https_handler, auth_handler)
        else:
            opener = urllib.request.build_opener(https_handler)
        urllib.request.install_opener(opener)


def calculate_local_file(plugin, version, extension):
    """
    calculate a local path for a plugin jar or pom
    :param plugin: plugin string in maven format (group:id)
    :param version: the snapshot version we want to download
    :param extension: what we are looking for (pom or jar)
    :return:
    """
    home = expanduser("~")
    group, id = plugin.split(':', 2)
    group_path = group.replace('.', os.path.sep)
    file = '%s-%s.%s' % (id, version, extension)
    return os.path.join(home, '.m2', 'repository', group_path, id, version, file)


def calculate_nexus_url_for_metadata(nexus_domain, plugin, version):
    """

    :param nexus_domain: the nexus server containing the plugin
    :param plugin: plugin string in maven format (group:id)
    :param version: the snapshot version we want to download
    :return:
    """
    # https://artifacts-zl.talend.com/nexus/content/repositories/snapshots/org/talend/ci/signer-maven-plugin/7.3.1-SNAPSHOT/maven-metadata.xml
    return calculate_nexus_url(nexus_domain, plugin, version, 'maven-metadata.xml')


def calculate_nexus_url_for_jar(nexus_domain, plugin, version, timestamp):
    """
    calculate nexus url for the jar of the plugin

    :param nexus_domain: the nexus server containing the plugin
    :param plugin: plugin string in maven format (group:id)
    :param version: the snapshot version we want to download
    :param timestamp: the specific timestamp we want to download
    :return:
    """
    group, id = plugin.split(':', 2)
    jar = '%s-%s.jar' % (id, timestamp)
    return calculate_nexus_url(nexus_domain, plugin, version, jar)


def calculate_nexus_url_for_pom(nexus_domain, plugin, version, timestamp):
    """
    calculate nexus url for the pom of the plugin

    :param nexus_domain: the nexus server containing the plugin
    :param plugin: plugin string in maven format (group:id)
    :param version: the snapshot version we want to download
    :param timestamp: the specific timestamp we want to download
    :return:
    """
    # https://artifacts-zl.talend.com/nexus/content/repositories/snapshots/org/talend/ci/signer-maven-plugin/7.3.1-SNAPSHOT/signer-maven-plugin-7.3.1-20191022.225015-86.pom
    group, id = plugin.split(':', 2)
    pom = '%s-%s.pom' % (id, timestamp)
    return calculate_nexus_url(nexus_domain, plugin, version, pom)


def calculate_nexus_url(nexus_domain, plugin, version, file):
    """
    calculate nexus url for a specific plugin

    :param nexus_domain: the nexus server containing the plugin
    :param plugin: plugin string in maven format (group:id)
    :param version: version of the plugin
    :param file: specific file we are looking for this plugin
    :return:
    """
    group, id = plugin.split(':', 2)
    group_path = group.replace('.', '/')
    return 'https://%s/nexus/content/repositories/snapshots/%s/%s/%s/%s' % (nexus_domain, group_path, id, version, file)


def get_timestamp(nexus_domain, plugin, plugins_version):
    """
    read maven metadata to get the timestamp of the snapshot we want.
    This is needed to be able to download the plugin as we need to find the url of the pom and jar files.

    :param nexus_domain: the nexus server containing the plugin
    :param plugin:
    :param plugins_version:
    :return:
    """
    metadata_url = calculate_nexus_url_for_metadata(nexus_domain, plugin, plugins_version)
    print("loading metadata %s" % metadata_url)
    with urllib.request.urlopen(metadata_url) as response:
        root = ElementTree.parse(response).getroot()
        versions = root.findall('versioning/snapshotVersions/snapshotVersion')
        jar = [item.find('value').text for item in versions if item.find('extension').text == 'jar'][0]
        pom = [item.find('value').text for item in versions if item.find('extension').text == 'pom'][0]
        print("found timestamp [pom:%s, jar:%s]" % (pom, jar))
        return pom, jar


def download_snapshot_talend_plugins(plugins_version, nexus_domain, overwrite_files):
    """
    download all Talend maven plugin used in studio to build/run a job.

    :param plugins_version: the version that will be downloaded
    :param nexus_domain: the nexus server containing the plugin
    :param overwrite_files: if we overwrite files or not.
    :return:
    """
    for plugin in TALEND_MAVEN_PLUGINS:
        timestamp_pom, timestamp_jar = get_timestamp(nexus_domain, plugin, plugins_version)
        download(calculate_nexus_url_for_pom(nexus_domain, plugin, plugins_version, timestamp_pom),
                 calculate_local_file(plugin, plugins_version, 'pom'),
                 overwrite_files)
        download(calculate_nexus_url_for_jar(nexus_domain, plugin, plugins_version, timestamp_jar),
                 calculate_local_file(plugin, plugins_version, 'jar'),
                 overwrite_files)


def update_fake_release_pom(plugin, snapshot_version, overwrite_files):
    """
    copy snapshot pom.xml to the release location and also adapt the name and the content of the file
    to be usable as a released jar.

    :param plugin:
    :param snapshot_version:
    :param overwrite_files:
    :return:
    """
    extension = 'pom'
    release_version = snapshot_version.replace('-SNAPSHOT', '')
    snapshot = calculate_local_file(plugin, snapshot_version, extension)
    fake_release = calculate_local_file(plugin, release_version, extension)
    ensure_directories_exist(fake_release)
    copy_with_version_filtering(snapshot, fake_release, snapshot_version, release_version, overwrite_files)


def copy_with_version_filtering(source_path, destination_path, original_text, replacement_text, overwrite_files):
    """
    copy a text file but filtering content by replacing all occurrence of original text
    by the new replacement text.

    :param source_path: the path of the source file
    :param destination_path: the path of the file to create
    :param original_text: the text to be replaced
    :param replacement_text: the text that will replace old one.
    """
    if not os.path.exists(destination_path) or overwrite_files:
        print("copy/adapt file %s%s%s" % (COLOR_CYAN, destination_path, END_COLOR))
        with open(source_path, 'r') as source, open(destination_path, 'w') as destination:
            while True:
                line = source.readline()
                if not line:
                    break
                destination.write(line.replace(original_text, replacement_text))
    else:
        print('%s%s%s file already exists' % (COLOR_CYAN, destination_path, END_COLOR))


def update_fake_release_jar(plugin, snapshot_version, overwrite_files):
    """
    copy snapshot plugin jar to the release location and also adapt the name and the content of the plugin archive
    to be usable as a released plugin.
    :param plugin: the plugin name as a maven string format (group:id)
    :param snapshot_version: the snapshot version to use as a source
    :param overwrite_files: should we overwrite existing files or not
    """
    extension = 'jar'
    release_version = snapshot_version.replace('-SNAPSHOT', '')
    fake_release_jar = calculate_local_file(plugin, release_version, extension)
    snapshot_jar = calculate_local_file(plugin, snapshot_version, extension)
    ensure_directories_exist(fake_release_jar)
    if not os.path.exists(fake_release_jar) or overwrite_files:
        if os.path.isfile(fake_release_jar):
            os.remove(fake_release_jar)
        with tempfile.TemporaryDirectory() as temporary_directory:
            zipfile.ZipFile(snapshot_jar).extractall(temporary_directory)
            plugin_descriptor_path = os.path.join(temporary_directory, 'META-INF', 'maven', 'plugin.xml')
            plugin_snapshot_descriptor_path = plugin_descriptor_path + '.snapshot'
            shutil.move(plugin_descriptor_path, plugin_snapshot_descriptor_path)
            copy_with_version_filtering(plugin_snapshot_descriptor_path, plugin_descriptor_path, snapshot_version,
                                        release_version, overwrite_files)
            shutil.make_archive(fake_release_jar, 'zip', temporary_directory)
            shutil.move(fake_release_jar + '.zip', fake_release_jar)
    else:
        print('%s%s%s file already exists' % (COLOR_CYAN, fake_release_jar, END_COLOR))


def ensure_directories_exist(filename):
    """
    ensure all parent directory of a file exist.
    if a part of the path is not existing, create it recursively.
    :param filename the filename path.
    """
    if not os.path.exists(os.path.dirname(filename)):
        try:
            os.makedirs(os.path.dirname(filename))
        except OSError as exc:  # Guard against race condition
            if exc.errno != errno.EEXIST:
                raise


def fake_release_talend_maven_plugins(snapshot_version, overwrite_files):
    """
    copy/adapt files for each snapshot plugins
    :param snapshot_version:
    :param overwrite_files: should we overwrite existing files or not
    :return:
    """
    for plugin in TALEND_MAVEN_PLUGINS:
        update_fake_release_pom(plugin, snapshot_version, overwrite_files)
        update_fake_release_jar(plugin, snapshot_version, overwrite_files)


def _console_bar_progress(progress):
    """
    utility method to display or updates a console progress bar
    show a fine lloking progress for download

    Accepts a float between 0 and 1. Any int will be converted to a float.
    A value under 0 represents a 'halt'.
    A value at 1 or bigger represents 100%

    :param progress:
    :return:
    """
    barLength = 30  # Modify this to change the length of the progress bar
    status = ""
    if isinstance(progress, int):
        progress = float(progress)
    if not isinstance(progress, float):
        progress = 0
        status = "error: progress var must be float\r\n"
    if progress < 0:
        progress = 0
        status = "Halt...\r\n"
    if progress >= 1:
        progress = 1
        status = "Done...\r\n"
    block = int(round(barLength * progress))
    text = "\rPercent: [{0}] {1}{2}%{3} {4}".format("#" * block + "-" * (barLength - block), COLOR_BLUE,
                                                    round(progress * 100, 2), END_COLOR, status)
    sys.stdout.write(text)
    sys.stdout.flush()


def console_percentage_bar_progress(copied, is_last):
    if is_last:
        current_progress = 1
    else:
        current_progress = copied / UPDATE_SITE_SIZE
    _console_bar_progress(current_progress)


def download(plugin_url, filename, overwrite_files):
    """
    download a file from an url.
    :param plugin_url: the url where the file is located
    :param filename: the local file where the remote file is located
    :param overwrite_files: should we overwrite existing files or not
    :return:
    """
    print("downloading %s%s%s to local file %s%s%s" % (
        COLOR_BLUE, plugin_url, END_COLOR, COLOR_CYAN, filename, END_COLOR))
    if not os.path.exists(os.path.dirname(filename)):
        try:
            os.makedirs(os.path.dirname(filename))
        except OSError as exc:  # Guard against race condition
            if exc.errno != errno.EEXIST:
                raise
    if not os.path.exists(filename) or overwrite_files:
        # Download the file from `url` and save it locally under `file_name`:
        with urllib.request.urlopen(plugin_url) as response, open(filename, 'wb') as destination:
            copyfileobj(response, destination, console_percentage_bar_progress)
    else:
        print('%s%s%s file already exists' % (COLOR_CYAN, filename, END_COLOR))


def copyfileobj(fsrc, fdst, callback, length=1024 * 1024):
    """
    copy from a stream object to another stream object.
    :param fsrc: source object
    :param fdst: destination object
    :param callback: method that is called on each step of the copy (allow progress indicator)
    :param length: size of chunk that are read on each step.
    :return:
    """
    copied = 0
    while True:
        buf = fsrc.read(length)
        if not buf:
            break
        fdst.write(buf)
        copied += len(buf)
        callback(copied, False)
    callback(copied, True)
    return True


def read_pom_version():
    """
    quick parse of pom file to find the project version
    :return: declared version
    """
    root = ElementTree.parse('pom.xml').getroot()
    return root.findall(VERSION)[0].text


if __name__ == '__main__':
    parser = argparse.ArgumentParser(
        description='download if necessary snapshot talend maven plugins and fake them as release ones.')
    parser.add_argument('-v', '--plugin-version', help='choose which version to fake', dest='plugin_version', type=str)
    parser.add_argument('--nexus-domain', '-n', type=str, help='nexus domain server', default='artifacts-zl.talend.com')
    parser.add_argument('--nexus-user', '-u', type=str, help='nexus user')
    parser.add_argument('--nexus-password', '-p', type=str, help='nexus password')
    parser.add_argument('--overwrite-files', '-o', action='store_true', help='overwrite existing plugins')
    args = parser.parse_args()
    install_opener(args.nexus_domain, args.nexus_user, args.nexus_password)
    if args.plugin_version is None:
        version = read_pom_version()
    else:
        version = args.version
    download_snapshot_talend_plugins(version, args.nexus_domain, args.overwrite_files)
    fake_release_talend_maven_plugins(version, args.overwrite_files)
