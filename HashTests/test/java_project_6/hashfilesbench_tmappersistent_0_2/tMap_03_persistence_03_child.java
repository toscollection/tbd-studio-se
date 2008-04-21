// ============================================================================
package java_project_6.hashfilesbench_tmappersistent_0_2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.talend.designer.components.persistent.PersistentSortedAdvancedLookup;

import routines.TalendString;
import routines.system.ParserUtils;
import routines.system.TDieException;

//the import part of tJavaFlex_2
//import java.util.List;

//the import part of tJavaFlex_3
//import java.util.List;

//the import part of tJavaFlex_4
//import java.util.List;

/**
 * Job: tMap_03_persistence_03_child Purpose: <br>
 * Description: <br>
 * 
 * @author amaumont@talend.com
 * @version 2.2.0.qualifier
 * @status
 */
public class tMap_03_persistence_03_child {
	// create and load default properties
	private static java.util.Properties defaultProps = new java.util.Properties();

	// create application properties with default
	private static class ContextProperties extends java.util.Properties {

		public ContextProperties(java.util.Properties properties) {
			super(properties);
		}

		public ContextProperties() {
			super();
		}

		public static String param_file_path;
		public static String data_dir;
		public static String data_output_dir;
		public static String result_host;
		public static String result_port;
		public static String result_database;
		public static String result_username;
		public static String result_password;
		public static String result_table;
		public static String mysql_host;
		public static String mysql_port;
		public static String mysql_schema;
		public static String mysql_username;
		public static String mysql_password;
		public static String current_job_dir;
		public static String indexMain;
		public static String indexLookup;
	}

	private static ContextProperties context = new ContextProperties();
	private static final String jobName = "tMap_03_persistence_03_child";
	private static final String projectName = "JAVA_PROJECT_6";
	public static Integer errorCode = null;
	private static String currentComponent = "";
	private static final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
	private static final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
	private static final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
	private static final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
	public static final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

	private class TalendException extends Exception {
		private Exception e = null;
		private tMap_03_persistence_03_child c = null;

		private TalendException(tMap_03_persistence_03_child c, Exception e) {
			this.e = e;
			this.c = c;
		}

		@Override
		public void printStackTrace() {
			if (!(e instanceof TalendException || e instanceof TDieException)) {
				globalMap.put(currentComponent + "_ERROR_MESSAGE", e
						.getMessage());
				System.err
						.println("Exception in component " + currentComponent);
			}
			if (!(e instanceof TDieException)) {
				e.printStackTrace();
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass()
							.getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(c, new Object[] { e });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
					}
				} catch (java.lang.SecurityException e) {
					this.e.printStackTrace();
				} catch (java.lang.IllegalArgumentException e) {
					this.e.printStackTrace();
				} catch (java.lang.IllegalAccessException e) {
					this.e.printStackTrace();
				} catch (java.lang.reflect.InvocationTargetException e) {
					this.e.printStackTrace();
				}
			}
		}
	}

	public void tAssert_7_error(Exception exception) throws TalendException {
		end_Hash.put("tAssert_7", System.currentTimeMillis());
		tAssert_7_onSubJobError(exception);
	}

	public void tFileCompare_1_error(Exception exception)
			throws TalendException {
		end_Hash.put("tFileCompare_1", System.currentTimeMillis());
		tFileCompare_1_onSubJobError(exception);
	}

	public void tJavaFlex_4_error(Exception exception) throws TalendException {
		end_Hash.put("tJavaFlex_4", System.currentTimeMillis());
		tJavaFlex_4_onSubJobError(exception);
	}

	public void tFixedFlowInput_1_error(Exception exception)
			throws TalendException {
		end_Hash.put("tFixedFlowInput_1", System.currentTimeMillis());
		tJavaFlex_4_onSubJobError(exception);
	}

	public void tBufferOutput_1_error(Exception exception)
			throws TalendException {
		end_Hash.put("tBufferOutput_1", System.currentTimeMillis());
		tJavaFlex_4_onSubJobError(exception);
	}

	public void tFileInputDelimited_2_error(Exception exception)
			throws TalendException {
		end_Hash.put("tFileInputDelimited_2", System.currentTimeMillis());
		tFileInputDelimited_2_onSubJobError(exception);
	}

	public void tContextLoad_1_error(Exception exception)
			throws TalendException {
		end_Hash.put("tContextLoad_1", System.currentTimeMillis());
		tFileInputDelimited_2_onSubJobError(exception);
	}

	public void tJavaFlex_3_error(Exception exception) throws TalendException {
		end_Hash.put("tJavaFlex_3", System.currentTimeMillis());
		tJavaFlex_3_onSubJobError(exception);
	}

	public void tFileInputDelimited_8_error(Exception exception)
			throws TalendException {
		end_Hash.put("tFileInputDelimited_8", System.currentTimeMillis());
		tFileInputDelimited_8_onSubJobError(exception);
	}

	public void tMapPersistent_1_error(Exception exception)
			throws TalendException {
		end_Hash.put("tMapPersistent_1", System.currentTimeMillis());
		tFileInputDelimited_8_onSubJobError(exception);
	}

	public void tFileOutputDelimited_2_error(Exception exception)
			throws TalendException {
		end_Hash.put("tFileOutputDelimited_2", System.currentTimeMillis());
		tFileInputDelimited_8_onSubJobError(exception);
	}

	public void tFileInputDelimited_9_error(Exception exception)
			throws TalendException {
		end_Hash.put("tFileInputDelimited_9", System.currentTimeMillis());
		tFileInputDelimited_8_onSubJobError(exception);
	}

	public void tAdvancedHashPersistent_lookup_error(Exception exception)
			throws TalendException {
		end_Hash.put("tAdvancedHashPersistent_lookup", System
				.currentTimeMillis());
		tFileInputDelimited_8_onSubJobError(exception);
	}

	public void tAssert_7_onSubJobError(Exception exception)
			throws TalendException {
	}

	public void tFileCompare_1_onSubJobError(Exception exception)
			throws TalendException {
	}

	public void tJavaFlex_4_onSubJobError(Exception exception)
			throws TalendException {
	}

	public void tFileInputDelimited_2_onSubJobError(Exception exception)
			throws TalendException {
	}

	public void tJavaFlex_3_onSubJobError(Exception exception)
			throws TalendException {
	}

	public void tFileInputDelimited_8_onSubJobError(Exception exception)
			throws TalendException {
	}

	public void tAssert_7Process() throws TalendException {
		try {
			/**
			 * [tAssert_7 begin ] start
			 */

			ok_Hash.put("tAssert_7", false);
			start_Hash.put("tAssert_7", System.currentTimeMillis());
			currentComponent = "tAssert_7";

			/**
			 * [tAssert_7 begin ] stop
			 */
			/**
			 * [tAssert_7 main ] start
			 */

			currentComponent = "tAssert_7";

			/**
			 * [tAssert_7 main ] stop
			 */
			/**
			 * [tAssert_7 end ] start
			 */

			currentComponent = "tAssert_7";

			ok_Hash.put("tAssert_7", true);
			end_Hash.put("tAssert_7", System.currentTimeMillis());

			/**
			 * [tAssert_7 end ] stop
			 */

		} catch (Exception e) {

			throw new TalendException(this, e);
		}

	}

	public void tFileCompare_1Process() throws TalendException {
		try {

			/**
			 * [tFileCompare_1 begin ] start
			 */

			ok_Hash.put("tFileCompare_1", false);
			start_Hash.put("tFileCompare_1", System.currentTimeMillis());
			currentComponent = "tFileCompare_1";

			/**
			 * [tFileCompare_1 begin ] stop
			 */
			/**
			 * [tFileCompare_1 main ] start
			 */

			currentComponent = "tFileCompare_1";

			boolean result_tFileCompare_1 = true;

			if (result_tFileCompare_1) {

				java.io.BufferedReader file_tFileCompare_1 = new java.io.BufferedReader(
						new java.io.FileReader(context.data_output_dir
								+ context.current_job_dir + "out_main_"
								+ context.indexMain + "__lookup_"
								+ context.indexLookup + ".csv"));
				java.io.BufferedReader fileRef_tFileCompare_1 = new java.io.BufferedReader(
						new java.io.FileReader((String) context
								.getProperty("data_dir")
								+ context.current_job_dir
								+ "ref/out_main_"
								+ context.indexMain
								+ "__lookup_"
								+ context.indexLookup + ".csv"));
				String content_tFileCompare_1 = null, contentRef_tFileCompare_1 = null;
				while ((content_tFileCompare_1 = file_tFileCompare_1.readLine()) != null
						&& (contentRef_tFileCompare_1 = fileRef_tFileCompare_1
								.readLine()) != null) {
					if (content_tFileCompare_1
							.compareTo(contentRef_tFileCompare_1) != 0) {
						result_tFileCompare_1 = false;
						break;
					}
				}

				// Check if files has a different number of lines:
				if (content_tFileCompare_1 == null) {
					// This step is done in case of the while upper ignore
					// second part:
					contentRef_tFileCompare_1 = fileRef_tFileCompare_1
							.readLine();
				}
				if (content_tFileCompare_1 != null
						|| contentRef_tFileCompare_1 != null) {
					result_tFileCompare_1 = false;
				}

				file_tFileCompare_1.close();
				fileRef_tFileCompare_1.close();

			}

			String messagetFileCompare_1 = "";
			if (result_tFileCompare_1) {
				messagetFileCompare_1 = "[" + jobName + "." + currentComponent
						+ "] 'main_" + context.indexMain + ".csv' and 'lookup_"
						+ context.indexLookup + ".csv', OK";

			} else {
				messagetFileCompare_1 = "[" + jobName + "." + currentComponent
						+ "] Files differ: check files 'main_"
						+ context.indexMain + ".csv' and 'lookup_"
						+ context.indexLookup + ".csv', DIFFERENT";
			}
			globalMap.put("tFileCompare_1_DIFFERENCE", result_tFileCompare_1);

			System.out.println(messagetFileCompare_1);

			/**
			 * [tFileCompare_1 main ] stop
			 */
			/**
			 * [tFileCompare_1 end ] start
			 */

			currentComponent = "tFileCompare_1";

			ok_Hash.put("tFileCompare_1", true);
			end_Hash.put("tFileCompare_1", System.currentTimeMillis());

			/**
			 * [tFileCompare_1 end ] stop
			 */
			tAssert_7Process();
			tJavaFlex_4Process();

		} catch (Exception e) {

			throw new TalendException(this, e);
		}

	}

	private class row4Struct implements
			routines.system.IPersistableRow<row4Struct>, Comparable<row4Struct> {

		Boolean testOk;

		public void loadData(byte[] data) {

			ByteArrayInputStream bai = null;
			ObjectInputStream dis = null;
			byte[] byteArray = null;
			try {
				bai = new ByteArrayInputStream(data);
				dis = new ObjectInputStream(bai);

				int length = 0;

				length = dis.readByte();
				if (length == -1) {
					this.testOk = null;
				} else {
					this.testOk = dis.readBoolean();
				}

			} catch (IOException e) {
				throw new RuntimeException(e);

			} finally {
				if (dis != null) {
					try {
						dis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		public byte[] toData() {
			ByteArrayOutputStream bao = null;
			ObjectOutputStream dos = null;
			byte[] byteArray = null;
			try {
				bao = new ByteArrayOutputStream();
				dos = new ObjectOutputStream(bao);

				if (this.testOk == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.testOk);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				if (dos != null) {
					try {
						dos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return bao.toByteArray();
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");

			sb.append("testOk");
			sb.append("=");
			sb.append(String.valueOf(this.testOk));

			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row4Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2
						.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			// if (this.ignoreCase) {
			return string1.compareToIgnoreCase(string2);
			// } else {
			// return string1.compareTo(string2);
			// }
		}

	}

	public void tJavaFlex_4Process() throws TalendException {
		try {

			row4Struct row4 = new row4Struct();
			/**
			 * [tJavaFlex_4 begin ] start
			 */

			ok_Hash.put("tJavaFlex_4", false);
			start_Hash.put("tJavaFlex_4", System.currentTimeMillis());
			currentComponent = "tJavaFlex_4";

			// start part of your Java code

			/**
			 * [tJavaFlex_4 begin ] stop
			 */
			/**
			 * [tJavaFlex_4 main ] start
			 */

			currentComponent = "tJavaFlex_4";

			// here is the main part of the component,
			// a piece of code executed in the row
			// loop

			// Map parentGlobalMap =
			// java_project_6.tmap_03_persistence_01_0_1.tMap_03_persistence_01.globalMap;

			// if(((Boolean)globalMap.get("tFileCompare_1_DIFFERENCE"))) {
			// parentGlobalMap.put("validatesCasesCount",
			// (Integer)parentGlobalMap.get("validatesCasesCount") + 1);
			// }

			/**
			 * [tJavaFlex_4 main ] stop
			 */

			/**
			 * [tBufferOutput_1 begin ] start
			 */

			ok_Hash.put("tBufferOutput_1", false);
			start_Hash.put("tBufferOutput_1", System.currentTimeMillis());
			currentComponent = "tBufferOutput_1";

			/**
			 * [tBufferOutput_1 begin ] stop
			 */

			/**
			 * [tFixedFlowInput_1 begin ] start
			 */

			ok_Hash.put("tFixedFlowInput_1", false);
			start_Hash.put("tFixedFlowInput_1", System.currentTimeMillis());
			currentComponent = "tFixedFlowInput_1";

			globalMap.put("NB_LINE", 1);
			for (int i_tFixedFlowInput_1 = 0; i_tFixedFlowInput_1 < 1; i_tFixedFlowInput_1++) {

				row4.testOk = ((Boolean) globalMap
						.get("tFileCompare_1_DIFFERENCE"));

				/**
				 * [tFixedFlowInput_1 begin ] stop
				 */
				/**
				 * [tFixedFlowInput_1 main ] start
				 */

				currentComponent = "tFixedFlowInput_1";

				/**
				 * [tFixedFlowInput_1 main ] stop
				 */
				/**
				 * [tBufferOutput_1 main ] start
				 */

				currentComponent = "tBufferOutput_1";

				String[] row_tBufferOutput_1 = new String[] { "", };
				if (row4.testOk != null) {

					row_tBufferOutput_1[0] = String.valueOf(row4.testOk);

				}
				globalBuffer.add(row_tBufferOutput_1);

				/**
				 * [tBufferOutput_1 main ] stop
				 */

				/**
				 * [tFixedFlowInput_1 end ] start
				 */

				currentComponent = "tFixedFlowInput_1";

			}

			ok_Hash.put("tFixedFlowInput_1", true);
			end_Hash.put("tFixedFlowInput_1", System.currentTimeMillis());

			/**
			 * [tFixedFlowInput_1 end ] stop
			 */
			/**
			 * [tBufferOutput_1 end ] start
			 */

			currentComponent = "tBufferOutput_1";

			ok_Hash.put("tBufferOutput_1", true);
			end_Hash.put("tBufferOutput_1", System.currentTimeMillis());

			/**
			 * [tBufferOutput_1 end ] stop
			 */

			/**
			 * [tJavaFlex_4 end ] start
			 */

			currentComponent = "tJavaFlex_4";

			// end of the component, outside/closing the loop

			ok_Hash.put("tJavaFlex_4", true);
			end_Hash.put("tJavaFlex_4", System.currentTimeMillis());

			/**
			 * [tJavaFlex_4 end ] stop
			 */

		} catch (Exception e) {

			throw new TalendException(this, e);
		}

	}

	private class row6Struct implements
			routines.system.IPersistableRow<row6Struct>, Comparable<row6Struct> {

		String key;

		String value;

		public void loadData(byte[] data) {

			ByteArrayInputStream bai = null;
			ObjectInputStream dis = null;
			byte[] byteArray = null;
			try {
				bai = new ByteArrayInputStream(data);
				dis = new ObjectInputStream(bai);

				int length = 0;

				length = dis.readInt();
				if (length == -1) {
					this.key = null;
				} else {
					byteArray = new byte[length];
					dis.read(byteArray);
					this.key = new String(byteArray);
				}

				length = dis.readInt();
				if (length == -1) {
					this.value = null;
				} else {
					byteArray = new byte[length];
					dis.read(byteArray);
					this.value = new String(byteArray);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);

			} finally {
				if (dis != null) {
					try {
						dis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		public byte[] toData() {
			ByteArrayOutputStream bao = null;
			ObjectOutputStream dos = null;
			byte[] byteArray = null;
			try {
				bao = new ByteArrayOutputStream();
				dos = new ObjectOutputStream(bao);

				if (this.key == null) {
					dos.writeInt(-1);
				} else {
					byteArray = this.key.getBytes();
					dos.writeInt(byteArray.length);
					dos.write(byteArray);
				}

				if (this.value == null) {
					dos.writeInt(-1);
				} else {
					byteArray = this.value.getBytes();
					dos.writeInt(byteArray.length);
					dos.write(byteArray);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				if (dos != null) {
					try {
						dos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return bao.toByteArray();
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");

			sb.append("key");
			sb.append("=");
			sb.append(String.valueOf(this.key));

			sb.append(", ");

			sb.append("value");
			sb.append("=");
			sb.append(String.valueOf(this.value));

			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row6Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2
						.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			// if (this.ignoreCase) {
			return string1.compareToIgnoreCase(string2);
			// } else {
			// return string1.compareTo(string2);
			// }
		}

	}

	public void tFileInputDelimited_2Process() throws TalendException {
		try {

			row6Struct row6 = new row6Struct();

			/**
			 * [tContextLoad_1 begin ] start
			 */

			ok_Hash.put("tContextLoad_1", false);
			start_Hash.put("tContextLoad_1", System.currentTimeMillis());
			currentComponent = "tContextLoad_1";

			java.util.List<String> assignList_tContextLoad_1 = new java.util.ArrayList<String>();
			java.util.List<String> newPropertyList_tContextLoad_1 = new java.util.ArrayList<String>();
			java.util.List<String> noAssignList_tContextLoad_1 = new java.util.ArrayList<String>();
			int nb_line_tContextLoad_1 = 0;
			/**
			 * [tContextLoad_1 begin ] stop
			 */

			/**
			 * [tFileInputDelimited_2 begin ] start
			 */

			ok_Hash.put("tFileInputDelimited_2", false);
			start_Hash.put("tFileInputDelimited_2", System.currentTimeMillis());
			currentComponent = "tFileInputDelimited_2";

			int nb_line_tFileInputDelimited_2 = 0;
			int footer_tFileInputDelimited_2 = 0;
			int totalLinetFileInputDelimited_2 = 0;
			int limittFileInputDelimited_2 = -1;
			int lastLinetFileInputDelimited_2 = -1;

			com.csvreader.CsvReader csvReadertFileInputDelimited_2 = new com.csvreader.CsvReader(
					new java.io.BufferedReader(new java.io.InputStreamReader(
							new java.io.FileInputStream(/**
														 * Start field
														 * tFileInputDelimited_2:FILENAME
														 */
							context.param_file_path/**
													 * End field
													 * tFileInputDelimited_2:FILENAME
													 */
							), "ISO-8859-15")), ',');

			csvReadertFileInputDelimited_2.setSkipEmptyRecords(true);
			csvReadertFileInputDelimited_2.setTextQualifier('"');

			csvReadertFileInputDelimited_2
					.setEscapeMode(com.csvreader.CsvReader.ESCAPE_MODE_DOUBLED);

			String[] rowtFileInputDelimited_2 = null;

			if (footer_tFileInputDelimited_2 > 0) {

				while (csvReadertFileInputDelimited_2.readRecord()) {
					rowtFileInputDelimited_2 = csvReadertFileInputDelimited_2
							.getValues();
					if (!(rowtFileInputDelimited_2.length == 1 && rowtFileInputDelimited_2[0]
							.equals("\015"))) {// empty line when row separator
												// is '\n'

						totalLinetFileInputDelimited_2++;

					}

				}
				int lastLineTemptFileInputDelimited_2 = totalLinetFileInputDelimited_2
						- footer_tFileInputDelimited_2 < 0 ? 0
						: totalLinetFileInputDelimited_2
								- footer_tFileInputDelimited_2;
				if (lastLinetFileInputDelimited_2 > 0) {
					lastLinetFileInputDelimited_2 = lastLinetFileInputDelimited_2 < lastLineTemptFileInputDelimited_2 ? lastLinetFileInputDelimited_2
							: lastLineTemptFileInputDelimited_2;
				} else {
					lastLinetFileInputDelimited_2 = lastLineTemptFileInputDelimited_2;
				}

				csvReadertFileInputDelimited_2.close();

				csvReadertFileInputDelimited_2 = new com.csvreader.CsvReader(
						new java.io.BufferedReader(
								new java.io.InputStreamReader(
										new java.io.FileInputStream(/**
																	 * Start
																	 * field
																	 * tFileInputDelimited_2:FILENAME
																	 */
										context.param_file_path/**
																 * End field
																 * tFileInputDelimited_2:FILENAME
																 */
										), "ISO-8859-15")), ',');
				csvReadertFileInputDelimited_2.setSkipEmptyRecords(true);
				csvReadertFileInputDelimited_2.setTextQualifier('"');

				csvReadertFileInputDelimited_2
						.setEscapeMode(com.csvreader.CsvReader.ESCAPE_MODE_DOUBLED);

			}

			int currentLinetFileInputDelimited_2 = 0;
			int outputLinetFileInputDelimited_2 = 0;

			while (csvReadertFileInputDelimited_2.readRecord()) {

				rowtFileInputDelimited_2 = csvReadertFileInputDelimited_2
						.getValues();

				if (rowtFileInputDelimited_2.length == 1
						&& rowtFileInputDelimited_2[0].equals("\015")) {// empty
																		// line
																		// when
																		// row
																		// separator
																		// is
																		// '\n'
					continue;
				}

				currentLinetFileInputDelimited_2++;
				if (currentLinetFileInputDelimited_2 < 0 + 1) {
					continue;
				}

				if (lastLinetFileInputDelimited_2 > -1
						&& currentLinetFileInputDelimited_2 > lastLinetFileInputDelimited_2) {
					break;
				}
				outputLinetFileInputDelimited_2++;
				if (limittFileInputDelimited_2 != -1
						&& outputLinetFileInputDelimited_2 > limittFileInputDelimited_2) {
					break;
				}

				row6 = null;
				boolean whetherReject_tFileInputDelimited_2 = false;
				row6 = new row6Struct();
				try {

					if (rowtFileInputDelimited_2.length == 1
							&& rowtFileInputDelimited_2[0].equals("\015")) {// empty
																			// line
																			// when
																			// row
																			// separator
																			// is
																			// '\n'

						row6.key = null;

						row6.value = null;

					} else {

						if (0 < rowtFileInputDelimited_2.length) {
							row6.key = rowtFileInputDelimited_2[0].trim();

						} else {

							row6.key = null;

						}

						if (1 < rowtFileInputDelimited_2.length) {
							row6.value = rowtFileInputDelimited_2[1].trim();

						} else {

							row6.value = null;

						}

					}

				} catch (Exception e) {
					whetherReject_tFileInputDelimited_2 = true;
					throw (e);
				}

				/**
				 * [tFileInputDelimited_2 begin ] stop
				 */
				/**
				 * [tFileInputDelimited_2 main ] start
				 */

				currentComponent = "tFileInputDelimited_2";

				/**
				 * [tFileInputDelimited_2 main ] stop
				 */
				// Start of branch "row6"
				if (row6 != null) {

					/**
					 * [tContextLoad_1 main ] start
					 */

					currentComponent = "tContextLoad_1";

					// ////////////////////////
					String key_tContextLoad_1 = null;
					if (row6.key != null) {
						key_tContextLoad_1 = row6.key;
					}
					String value_tContextLoad_1 = null;
					if (row6.value != null) {
						value_tContextLoad_1 = row6.value;
					}
					if (context.getProperty(key_tContextLoad_1) != null) {
						context.setProperty(key_tContextLoad_1,
								value_tContextLoad_1);

						if (key_tContextLoad_1 != null
								&& "param_file_path".equals(key_tContextLoad_1)) {
							context.param_file_path = value_tContextLoad_1;
						}

						if (key_tContextLoad_1 != null
								&& "data_dir".equals(key_tContextLoad_1)) {
							context.data_dir = value_tContextLoad_1;
						}

						if (key_tContextLoad_1 != null
								&& "data_output_dir".equals(key_tContextLoad_1)) {
							context.data_output_dir = value_tContextLoad_1;
						}

						if (key_tContextLoad_1 != null
								&& "result_host".equals(key_tContextLoad_1)) {
							context.result_host = value_tContextLoad_1;
						}

						if (key_tContextLoad_1 != null
								&& "result_port".equals(key_tContextLoad_1)) {
							context.result_port = value_tContextLoad_1;
						}

						if (key_tContextLoad_1 != null
								&& "result_database".equals(key_tContextLoad_1)) {
							context.result_database = value_tContextLoad_1;
						}

						if (key_tContextLoad_1 != null
								&& "result_username".equals(key_tContextLoad_1)) {
							context.result_username = value_tContextLoad_1;
						}

						if (key_tContextLoad_1 != null
								&& "result_password".equals(key_tContextLoad_1)) {
							context.result_password = value_tContextLoad_1;
						}

						if (key_tContextLoad_1 != null
								&& "result_table".equals(key_tContextLoad_1)) {
							context.result_table = value_tContextLoad_1;
						}

						if (key_tContextLoad_1 != null
								&& "mysql_host".equals(key_tContextLoad_1)) {
							context.mysql_host = value_tContextLoad_1;
						}

						if (key_tContextLoad_1 != null
								&& "mysql_port".equals(key_tContextLoad_1)) {
							context.mysql_port = value_tContextLoad_1;
						}

						if (key_tContextLoad_1 != null
								&& "mysql_schema".equals(key_tContextLoad_1)) {
							context.mysql_schema = value_tContextLoad_1;
						}

						if (key_tContextLoad_1 != null
								&& "mysql_username".equals(key_tContextLoad_1)) {
							context.mysql_username = value_tContextLoad_1;
						}

						if (key_tContextLoad_1 != null
								&& "mysql_password".equals(key_tContextLoad_1)) {
							context.mysql_password = value_tContextLoad_1;
						}

						if (key_tContextLoad_1 != null
								&& "current_job_dir".equals(key_tContextLoad_1)) {
							context.current_job_dir = value_tContextLoad_1;
						}

						if (key_tContextLoad_1 != null
								&& "indexMain".equals(key_tContextLoad_1)) {
							context.indexMain = value_tContextLoad_1;
						}

						if (key_tContextLoad_1 != null
								&& "indexLookup".equals(key_tContextLoad_1)) {
							context.indexLookup = value_tContextLoad_1;
						}

						assignList_tContextLoad_1.add(key_tContextLoad_1);
					} else {
						context.setProperty(key_tContextLoad_1,
								value_tContextLoad_1);

						if (key_tContextLoad_1 != null
								&& "param_file_path".equals(key_tContextLoad_1)) {
							context.param_file_path = value_tContextLoad_1;

						}

						if (key_tContextLoad_1 != null
								&& "data_dir".equals(key_tContextLoad_1)) {
							context.data_dir = value_tContextLoad_1;

						}

						if (key_tContextLoad_1 != null
								&& "data_output_dir".equals(key_tContextLoad_1)) {
							context.data_output_dir = value_tContextLoad_1;

						}

						if (key_tContextLoad_1 != null
								&& "result_host".equals(key_tContextLoad_1)) {
							context.result_host = value_tContextLoad_1;

						}

						if (key_tContextLoad_1 != null
								&& "result_port".equals(key_tContextLoad_1)) {
							context.result_port = value_tContextLoad_1;

						}

						if (key_tContextLoad_1 != null
								&& "result_database".equals(key_tContextLoad_1)) {
							context.result_database = value_tContextLoad_1;

						}

						if (key_tContextLoad_1 != null
								&& "result_username".equals(key_tContextLoad_1)) {
							context.result_username = value_tContextLoad_1;

						}

						if (key_tContextLoad_1 != null
								&& "result_password".equals(key_tContextLoad_1)) {
							context.result_password = value_tContextLoad_1;

						}

						if (key_tContextLoad_1 != null
								&& "result_table".equals(key_tContextLoad_1)) {
							context.result_table = value_tContextLoad_1;

						}

						if (key_tContextLoad_1 != null
								&& "mysql_host".equals(key_tContextLoad_1)) {
							context.mysql_host = value_tContextLoad_1;

						}

						if (key_tContextLoad_1 != null
								&& "mysql_port".equals(key_tContextLoad_1)) {
							context.mysql_port = value_tContextLoad_1;

						}

						if (key_tContextLoad_1 != null
								&& "mysql_schema".equals(key_tContextLoad_1)) {
							context.mysql_schema = value_tContextLoad_1;

						}

						if (key_tContextLoad_1 != null
								&& "mysql_username".equals(key_tContextLoad_1)) {
							context.mysql_username = value_tContextLoad_1;

						}

						if (key_tContextLoad_1 != null
								&& "mysql_password".equals(key_tContextLoad_1)) {
							context.mysql_password = value_tContextLoad_1;

						}

						if (key_tContextLoad_1 != null
								&& "current_job_dir".equals(key_tContextLoad_1)) {
							context.current_job_dir = value_tContextLoad_1;

						}

						if (key_tContextLoad_1 != null
								&& "indexMain".equals(key_tContextLoad_1)) {
							context.indexMain = value_tContextLoad_1;

						}

						if (key_tContextLoad_1 != null
								&& "indexLookup".equals(key_tContextLoad_1)) {
							context.indexLookup = value_tContextLoad_1;

						}

						newPropertyList_tContextLoad_1.add(key_tContextLoad_1);
					}
					nb_line_tContextLoad_1++;
					// ////////////////////////
					/**
					 * [tContextLoad_1 main ] stop
					 */

				} // End of branch "row6"

				/**
				 * [tFileInputDelimited_2 end ] start
				 */

				currentComponent = "tFileInputDelimited_2";

				nb_line_tFileInputDelimited_2++;
			}
			csvReadertFileInputDelimited_2.close();
			globalMap.put("tFileInputDelimited_2_NB_LINE",
					nb_line_tFileInputDelimited_2);

			ok_Hash.put("tFileInputDelimited_2", true);
			end_Hash.put("tFileInputDelimited_2", System.currentTimeMillis());

			/**
			 * [tFileInputDelimited_2 end ] stop
			 */
			/**
			 * [tContextLoad_1 end ] start
			 */

			currentComponent = "tContextLoad_1";

			java.util.Enumeration<?> enu_tContextLoad_1 = context
					.propertyNames();
			while (enu_tContextLoad_1.hasMoreElements()) {
				String key_tContextLoad_1 = (String) enu_tContextLoad_1
						.nextElement();
				if (!assignList_tContextLoad_1.contains(key_tContextLoad_1)
						&& !newPropertyList_tContextLoad_1
								.contains(key_tContextLoad_1)) {
					noAssignList_tContextLoad_1.add(key_tContextLoad_1);
				}
			}
			globalMap.put("tContextLoad_1_NB_LINE", nb_line_tContextLoad_1);

			ok_Hash.put("tContextLoad_1", true);
			end_Hash.put("tContextLoad_1", System.currentTimeMillis());

			/**
			 * [tContextLoad_1 end ] stop
			 */

			tJavaFlex_3Process();

		} catch (Exception e) {

			throw new TalendException(this, e);
		}

	}

	public void tJavaFlex_3Process() throws TalendException {
		try {

			/**
			 * [tJavaFlex_3 begin ] start
			 */

			ok_Hash.put("tJavaFlex_3", false);
			start_Hash.put("tJavaFlex_3", System.currentTimeMillis());
			currentComponent = "tJavaFlex_3";

			// start part of your Java code

			/**
			 * [tJavaFlex_3 begin ] stop
			 */
			/**
			 * [tJavaFlex_3 main ] start
			 */

			currentComponent = "tJavaFlex_3";

			// here is the main part of the component,
			// a piece of code executed in the row
			// loop
			System.out.println("Testing with files 'main_" + context.indexMain
					+ ".csv' and 'lookup_" + context.indexLookup + ".csv' ...");

			/**
			 * [tJavaFlex_3 main ] stop
			 */
			/**
			 * [tJavaFlex_3 end ] start
			 */

			currentComponent = "tJavaFlex_3";

			// end of the component, outside/closing the loop

			ok_Hash.put("tJavaFlex_3", true);
			end_Hash.put("tJavaFlex_3", System.currentTimeMillis());

			/**
			 * [tJavaFlex_3 end ] stop
			 */
			tFileInputDelimited_8Process();

		} catch (Exception e) {

			throw new TalendException(this, e);
		}

	}

	private class outStruct implements
			routines.system.IPersistableRow<outStruct>, Comparable<outStruct> {

		Integer ID_MAIN;

		String LABEL_MAIN;

		Integer ID_LOOKUP1;

		String LABEL_LOOKUP1;

		public void loadData(byte[] data) {

			ByteArrayInputStream bai = null;
			ObjectInputStream dis = null;
			byte[] byteArray = null;
			try {
				bai = new ByteArrayInputStream(data);
				dis = new ObjectInputStream(bai);

				int length = 0;

				length = dis.readByte();
				if (length == -1) {
					this.ID_MAIN = null;
				} else {
					this.ID_MAIN = dis.readInt();
				}

				length = dis.readInt();
				if (length == -1) {
					this.LABEL_MAIN = null;
				} else {
					byteArray = new byte[length];
					dis.read(byteArray);
					this.LABEL_MAIN = new String(byteArray);
				}

				length = dis.readByte();
				if (length == -1) {
					this.ID_LOOKUP1 = null;
				} else {
					this.ID_LOOKUP1 = dis.readInt();
				}

				length = dis.readInt();
				if (length == -1) {
					this.LABEL_LOOKUP1 = null;
				} else {
					byteArray = new byte[length];
					dis.read(byteArray);
					this.LABEL_LOOKUP1 = new String(byteArray);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);

			} finally {
				if (dis != null) {
					try {
						dis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		public byte[] toData() {
			ByteArrayOutputStream bao = null;
			ObjectOutputStream dos = null;
			byte[] byteArray = null;
			try {
				bao = new ByteArrayOutputStream();
				dos = new ObjectOutputStream(bao);

				if (this.ID_MAIN == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeInt(this.ID_MAIN);
				}

				if (this.LABEL_MAIN == null) {
					dos.writeInt(-1);
				} else {
					byteArray = this.LABEL_MAIN.getBytes();
					dos.writeInt(byteArray.length);
					dos.write(byteArray);
				}

				if (this.ID_LOOKUP1 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeInt(this.ID_LOOKUP1);
				}

				if (this.LABEL_LOOKUP1 == null) {
					dos.writeInt(-1);
				} else {
					byteArray = this.LABEL_LOOKUP1.getBytes();
					dos.writeInt(byteArray.length);
					dos.write(byteArray);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				if (dos != null) {
					try {
						dos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return bao.toByteArray();
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");

			sb.append("ID_MAIN");
			sb.append("=");
			sb.append(String.valueOf(this.ID_MAIN));

			sb.append(", ");

			sb.append("LABEL_MAIN");
			sb.append("=");
			sb.append(String.valueOf(this.LABEL_MAIN));

			sb.append(", ");

			sb.append("ID_LOOKUP1");
			sb.append("=");
			sb.append(String.valueOf(this.ID_LOOKUP1));

			sb.append(", ");

			sb.append("LABEL_LOOKUP1");
			sb.append("=");
			sb.append(String.valueOf(this.LABEL_LOOKUP1));

			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(outStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2
						.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			// if (this.ignoreCase) {
			return string1.compareToIgnoreCase(string2);
			// } else {
			// return string1.compareTo(string2);
			// }
		}

	}

	private class mainStruct implements
			routines.system.IPersistableRow<mainStruct>, Comparable<mainStruct> {

		Integer ID_MAIN;

		String LABEL_MAIN;

		public void loadData(byte[] data) {

			ByteArrayInputStream bai = null;
			ObjectInputStream dis = null;
			byte[] byteArray = null;
			try {
				bai = new ByteArrayInputStream(data);
				dis = new ObjectInputStream(bai);

				int length = 0;

				length = dis.readByte();
				if (length == -1) {
					this.ID_MAIN = null;
				} else {
					this.ID_MAIN = dis.readInt();
				}

				length = dis.readInt();
				if (length == -1) {
					this.LABEL_MAIN = null;
				} else {
					byteArray = new byte[length];
					dis.read(byteArray);
					this.LABEL_MAIN = new String(byteArray);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);

			} finally {
				if (dis != null) {
					try {
						dis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		public byte[] toData() {
			ByteArrayOutputStream bao = null;
			ObjectOutputStream dos = null;
			byte[] byteArray = null;
			try {
				bao = new ByteArrayOutputStream();
				dos = new ObjectOutputStream(bao);

				if (this.ID_MAIN == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeInt(this.ID_MAIN);
				}

				if (this.LABEL_MAIN == null) {
					dos.writeInt(-1);
				} else {
					byteArray = this.LABEL_MAIN.getBytes();
					dos.writeInt(byteArray.length);
					dos.write(byteArray);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				if (dos != null) {
					try {
						dos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return bao.toByteArray();
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");

			sb.append("ID_MAIN");
			sb.append("=");
			sb.append(String.valueOf(this.ID_MAIN));

			sb.append(", ");

			sb.append("LABEL_MAIN");
			sb.append("=");
			sb.append(String.valueOf(this.LABEL_MAIN));

			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(mainStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2
						.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			// if (this.ignoreCase) {
			return string1.compareToIgnoreCase(string2);
			// } else {
			// return string1.compareTo(string2);
			// }
		}

	}

	private class after_tFileInputDelimited_8Struct implements
			routines.system.IPersistableRow<after_tFileInputDelimited_8Struct>,
			Comparable<after_tFileInputDelimited_8Struct> {

		Integer ID_MAIN;

		String LABEL_MAIN;

		public void loadData(byte[] data) {

			ByteArrayInputStream bai = null;
			ObjectInputStream dis = null;
			byte[] byteArray = null;
			try {
				bai = new ByteArrayInputStream(data);
				dis = new ObjectInputStream(bai);

				int length = 0;

				length = dis.readByte();
				if (length == -1) {
					this.ID_MAIN = null;
				} else {
					this.ID_MAIN = dis.readInt();
				}

				length = dis.readInt();
				if (length == -1) {
					this.LABEL_MAIN = null;
				} else {
					byteArray = new byte[length];
					dis.read(byteArray);
					this.LABEL_MAIN = new String(byteArray);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);

			} finally {
				if (dis != null) {
					try {
						dis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		public byte[] toData() {
			ByteArrayOutputStream bao = null;
			ObjectOutputStream dos = null;
			byte[] byteArray = null;
			try {
				bao = new ByteArrayOutputStream();
				dos = new ObjectOutputStream(bao);

				if (this.ID_MAIN == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeInt(this.ID_MAIN);
				}

				if (this.LABEL_MAIN == null) {
					dos.writeInt(-1);
				} else {
					byteArray = this.LABEL_MAIN.getBytes();
					dos.writeInt(byteArray.length);
					dos.write(byteArray);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				if (dos != null) {
					try {
						dos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return bao.toByteArray();
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");

			sb.append("ID_MAIN");
			sb.append("=");
			sb.append(String.valueOf(this.ID_MAIN));

			sb.append(", ");

			sb.append("LABEL_MAIN");
			sb.append("=");
			sb.append(String.valueOf(this.LABEL_MAIN));

			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(after_tFileInputDelimited_8Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2
						.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			// if (this.ignoreCase) {
			return string1.compareToIgnoreCase(string2);
			// } else {
			// return string1.compareTo(string2);
			// }
		}

	}

	public void tFileInputDelimited_8Process() throws TalendException {
		try {
			tFileInputDelimited_9Process();

			mainStruct main = new mainStruct();
			outStruct out = new outStruct();

			/**
			 * [tFileOutputDelimited_2 begin ] start
			 */

			ok_Hash.put("tFileOutputDelimited_2", false);
			start_Hash
					.put("tFileOutputDelimited_2", System.currentTimeMillis());
			currentComponent = "tFileOutputDelimited_2";

			String fileName_tFileOutputDelimited_2 = (new java.io.File(
					context.data_output_dir + context.current_job_dir
							+ "out_main_" + context.indexMain + "__lookup_"
							+ context.indexLookup + ".csv")).getAbsolutePath()
					.replace("\\", "/");
			String fullName_tFileOutputDelimited_2 = null;
			String extension_tFileOutputDelimited_2 = null;
			String directory_tFileOutputDelimited_2 = null;
			if ((fileName_tFileOutputDelimited_2.indexOf("/") != -1)) {
				if (fileName_tFileOutputDelimited_2.lastIndexOf(".") < fileName_tFileOutputDelimited_2
						.lastIndexOf("/")) {
					fullName_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2;
					extension_tFileOutputDelimited_2 = "";
				} else {
					fullName_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2
							.substring(0, fileName_tFileOutputDelimited_2
									.lastIndexOf("."));
					extension_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2
							.substring(fileName_tFileOutputDelimited_2
									.lastIndexOf("."));
				}
				directory_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2
						.substring(0, fileName_tFileOutputDelimited_2
								.lastIndexOf("/"));
			} else {
				if (fileName_tFileOutputDelimited_2.lastIndexOf(".") != -1) {
					fullName_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2
							.substring(0, fileName_tFileOutputDelimited_2
									.lastIndexOf("."));
					extension_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2
							.substring(fileName_tFileOutputDelimited_2
									.lastIndexOf("."));
				} else {
					fullName_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2;
					extension_tFileOutputDelimited_2 = "";
				}
				directory_tFileOutputDelimited_2 = "";
			}

			int nb_line_tFileOutputDelimited_2 = 0;
			int splitEvery_tFileOutputDelimited_2 = 1000;
			int splitedFileNo_tFileOutputDelimited_2 = 0;
			int currentRow_tFileOutputDelimited_2 = 0;

			final String OUT_DELIM_tFileOutputDelimited_2 = /**
															 * Start field
															 * tFileOutputDelimited_2:FIELDSEPARATOR
															 */
			";"/** End field tFileOutputDelimited_2:FIELDSEPARATOR */
			;

			final String OUT_DELIM_ROWSEP_tFileOutputDelimited_2 = /**
																	 * Start
																	 * field
																	 * tFileOutputDelimited_2:ROWSEPARATOR
																	 */
			"\n"/** End field tFileOutputDelimited_2:ROWSEPARATOR */
			;

			// create directory only if not exists
			if (directory_tFileOutputDelimited_2 != null
					&& directory_tFileOutputDelimited_2.trim().length() != 0) {
				java.io.File dir_tFileOutputDelimited_2 = new java.io.File(
						directory_tFileOutputDelimited_2);
				if (!dir_tFileOutputDelimited_2.exists()) {
					dir_tFileOutputDelimited_2.mkdirs();
				}
			}

			java.io.BufferedWriter outtFileOutputDelimited_2 = new java.io.BufferedWriter(
					new java.io.OutputStreamWriter(
							new java.io.FileOutputStream(
									fileName_tFileOutputDelimited_2, false),
							"ISO-8859-15"));
			java.io.File filetFileOutputDelimited_2 = new java.io.File(
					fileName_tFileOutputDelimited_2);

			if (filetFileOutputDelimited_2.length() == 0) {

				outtFileOutputDelimited_2.write("ID_MAIN");

				outtFileOutputDelimited_2
						.write(OUT_DELIM_tFileOutputDelimited_2);

				outtFileOutputDelimited_2.write("LABEL_MAIN");

				outtFileOutputDelimited_2
						.write(OUT_DELIM_tFileOutputDelimited_2);

				outtFileOutputDelimited_2.write("ID_LOOKUP1");

				outtFileOutputDelimited_2
						.write(OUT_DELIM_tFileOutputDelimited_2);

				outtFileOutputDelimited_2.write("LABEL_LOOKUP1");

				outtFileOutputDelimited_2
						.write(OUT_DELIM_ROWSEP_tFileOutputDelimited_2);
			}

			/**
			 * [tFileOutputDelimited_2 begin ] stop
			 */

			/**
			 * [tMapPersistent_1 begin ] start
			 */

			ok_Hash.put("tMapPersistent_1", false);
			start_Hash.put("tMapPersistent_1", System.currentTimeMillis());
			currentComponent = "tMapPersistent_1";

			// ###############################
			// # Lookup's keys initialization

			PersistentSortedAdvancedLookup<lookupStruct> tHash_Lookup_lookup = (PersistentSortedAdvancedLookup<lookupStruct>) globalMap
					.get("tHash_Lookup_lookup");

			lookupStruct lookupHashKey = new lookupStruct();
			lookupStruct lookupDefault = new lookupStruct();
			// ###############################

			// ###############################
			// # Vars initialization
			// ###############################

			// ###############################
			// # Outputs initialization
			outStruct out_tmp = new outStruct();
			// ###############################

			class SortableRow_main implements Comparable<SortableRow_main>,
					routines.system.IPersistableRow { // G_TM_B_001

				boolean is__rejectedInnerJoin;

				Integer exprKey_lookup__ID_LOOKUP1;
				Integer main__ID_MAIN;
				String main__LABEL_MAIN;

				public void fillFrom(mainStruct main,
						Integer exprKey_lookup__ID_LOOKUP1) {

					this.main__ID_MAIN = main.ID_MAIN;
					this.main__LABEL_MAIN = main.LABEL_MAIN;
					this.exprKey_lookup__ID_LOOKUP1 = exprKey_lookup__ID_LOOKUP1;

				}

				public void copyDataTo(mainStruct main) {

					main.ID_MAIN = this.main__ID_MAIN;
					main.LABEL_MAIN = this.main__LABEL_MAIN;

				}

				public String toString() {

					StringBuilder sb = new StringBuilder();
					sb.append(super.toString());
					sb.append("[");

					sb.append("main__ID_MAIN");
					sb.append("=");
					sb.append(String.valueOf(this.main__ID_MAIN));

					sb.append(", ");

					sb.append("main__LABEL_MAIN");
					sb.append("=");
					sb.append(String.valueOf(this.main__LABEL_MAIN));

					sb.append("]");

					return sb.toString();
				}

				public int compareTo(SortableRow_main other) {

					int returnValue = 0;

					returnValue = checkNullsAndCompare(
							this.exprKey_lookup__ID_LOOKUP1,
							other.exprKey_lookup__ID_LOOKUP1);
					if (returnValue != 0) {
						return returnValue;
					}

					return returnValue;
				}

				private int checkNullsAndCompare(Object object1, Object object2) {

					int ascendingInt = 1;

					int returnValue = 0;
					if (object1 instanceof String && object2 instanceof String) {
						returnValue = compareStrings((String) object1,
								(String) object2);
					} else if (object1 instanceof Comparable
							&& object2 instanceof Comparable) {
						returnValue = ((Comparable) object1).compareTo(object2);
					} else if (object1 != null && object2 != null) {
						returnValue = compareStrings(object1.toString(),
								object2.toString());
					} else if (object1 == null && object2 != null) {
						returnValue = 1;
					} else if (object1 != null && object2 == null) {
						returnValue = -1;
					} else {
						returnValue = 0;
					}

					return ascendingInt * returnValue;
				}

				private int compareStrings(String string1, String string2) {
					boolean ignoreCase = true;
					if (ignoreCase) {
						return string1.compareToIgnoreCase(string2);
					} else {
						return string1.compareTo(string2);
					}
				}

				public void loadData(byte[] data) {

					ByteArrayInputStream bai = null;
					DataInputStream dis = null;
					byte[] byteArray = null;
					try {
						bai = new ByteArrayInputStream(data);
						dis = new DataInputStream(bai);

						int length = 0;

						this.is__rejectedInnerJoin = dis.readBoolean();

						length = dis.readByte();
						if (length == -1) {
							this.main__ID_MAIN = null;
						} else {
							this.main__ID_MAIN = dis.readInt();
						}

						length = dis.readInt();
						if (length == -1) {
							this.main__LABEL_MAIN = null;
						} else {
							byteArray = new byte[length];
							dis.read(byteArray);
							this.main__LABEL_MAIN = new String(byteArray);
						}

						length = dis.readByte();
						if (length == -1) {
							this.exprKey_lookup__ID_LOOKUP1 = null;
						} else {
							this.exprKey_lookup__ID_LOOKUP1 = dis.readInt();
						}

					} catch (IOException e) {
						throw new RuntimeException(e);

					} finally {
						if (dis != null) {
							try {
								dis.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}

				public byte[] toData() {
					ByteArrayOutputStream bao = null;
					DataOutputStream dos = null;
					byte[] byteArray = null;
					try {
						bao = new ByteArrayOutputStream();
						dos = new DataOutputStream(bao);

						dos.writeBoolean(this.is__rejectedInnerJoin);

						if (this.main__ID_MAIN == null) {
							dos.writeByte(-1);
						} else {
							dos.writeByte(0);
							dos.writeInt(this.main__ID_MAIN);
						}

						if (this.main__LABEL_MAIN == null) {
							dos.writeInt(-1);
						} else {
							byteArray = this.main__LABEL_MAIN.getBytes();
							dos.writeInt(byteArray.length);
							dos.write(byteArray);
						}

						if (this.exprKey_lookup__ID_LOOKUP1 == null) {
							dos.writeByte(-1);
						} else {
							dos.writeByte(0);
							dos.writeInt(this.exprKey_lookup__ID_LOOKUP1);
						}

					} catch (IOException e) {
						throw new RuntimeException(e);
					} finally {
						if (dos != null) {
							try {
								dos.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					return bao.toByteArray();
				}

			} // G_TM_B_001

			org.talend.designer.components.persistent.PersistentRowSorterIterator<SortableRow_main> fsi_main = new org.talend.designer.components.persistent.PersistentRowSorterIterator<SortableRow_main>(
					"/home/amaumont/data/dev/eclipse/workspaces/runtime-talend.product2"
							+ "/tMapData_main") {
				public SortableRow_main createRowInstance() {
					return new SortableRow_main();
				}
			};

			fsi_main.initPut();

			/**
			 * [tMapPersistent_1 begin ] stop
			 */

			/**
			 * [tFileInputDelimited_8 begin ] start
			 */

			ok_Hash.put("tFileInputDelimited_8", false);
			start_Hash.put("tFileInputDelimited_8", System.currentTimeMillis());
			currentComponent = "tFileInputDelimited_8";

			org.talend.fileprocess.FileInputDelimited fid_tFileInputDelimited_8 = new org.talend.fileprocess.FileInputDelimited(
					(String) context.getProperty("data_dir")
							+ context.current_job_dir + "in/main_"
							+ context.indexMain + ".csv", "ISO-8859-15", ";",
					"\n", true, 0, 0, -1, -1);
			while (fid_tFileInputDelimited_8.nextRecord()) {
				main = null;
				boolean whetherReject_tFileInputDelimited_8 = false;
				main = new mainStruct();
				try {
					String temp_tFileInputDelimited_8 = "";
					temp_tFileInputDelimited_8 = fid_tFileInputDelimited_8.get(
							0).trim();
					if (temp_tFileInputDelimited_8.length() > 0) {
						main.ID_MAIN = ParserUtils
								.parseTo_Integer(temp_tFileInputDelimited_8);
					} else {
						main.ID_MAIN = null;
					}
					main.LABEL_MAIN = fid_tFileInputDelimited_8.get(1).trim();

				} catch (Exception e) {
					whetherReject_tFileInputDelimited_8 = true;
					throw (e);
				}

				/**
				 * [tFileInputDelimited_8 begin ] stop
				 */
				/**
				 * [tFileInputDelimited_8 main ] start
				 */

				currentComponent = "tFileInputDelimited_8";

				/**
				 * [tFileInputDelimited_8 main ] stop
				 */
				// Start of branch "main"
				if (main != null) {

					/**
					 * [tMapPersistent_1 main ] start
					 */

					currentComponent = "tMapPersistent_1";

					Integer exprKey_lookup__ID_LOOKUP1 = main.ID_MAIN;

					SortableRow_main rsc_main = fsi_main.getNextFreeRow();

					rsc_main.fillFrom(main, exprKey_lookup__ID_LOOKUP1);

					fsi_main.put(rsc_main);

				}// End of branch temporary "main"

			} // end temporary main loop ##(1)##

			fsi_main.endPut();

			// ###############################
			// # Input tables (lookups)
			boolean rejectedInnerJoin_tMapPersistent_1 = false;
			boolean mainRowRejected_tMapPersistent_1 = false;

			// /////////////////////////////////////////////
			// Starting Lookup Table "lookup"
			// /////////////////////////////////////////////

			SortableRow_main rsc_main;

			{ // start main loop

				tHash_Lookup_lookup.initGet();

				// TEST firstLookupIsPersistentSorted=true
				// isFirstPersistentSortedTable=true

				rejectedInnerJoin_tMapPersistent_1 = false;

				while (fsi_main.hasNext()) { // G_TM_M_250 loop "main"

					rsc_main = (SortableRow_main) fsi_main.next();
					rsc_main.copyDataTo(main);

					rejectedInnerJoin_tMapPersistent_1 = rsc_main.is__rejectedInnerJoin;

					boolean forceLooplookup = false;

					lookupStruct lookupObjectFromLookup = null;

					if (!rejectedInnerJoin_tMapPersistent_1) { // G 20

						lookupHashKey.ID_LOOKUP1 = rsc_main.exprKey_lookup__ID_LOOKUP1;

						tHash_Lookup_lookup.lookup(lookupHashKey);

						if (!tHash_Lookup_lookup.hasNext()) { // G 090

						} // G 090

					} // G 20

					lookupStruct lookup = null;

					lookupStruct fromLookup_lookup = null;
					lookup = lookupDefault;

					if (tHash_Lookup_lookup.hasNext()) { // G 099

						fromLookup_lookup = tHash_Lookup_lookup.next();

					} // G 099

					if (fromLookup_lookup != null) {
						lookup = fromLookup_lookup;
					}

					// { // Dummy bracket to open block for "main" ##(1)##

					// ###############################
					{ // start of Var scope

						// ###############################
						// # Vars tables
						// ###############################
						// ###############################
						// # Output tables

						out = null;

						// # Output table : 'out'
						out_tmp.ID_MAIN = main.ID_MAIN;
						out_tmp.LABEL_MAIN = main.LABEL_MAIN;
						out_tmp.ID_LOOKUP1 = lookup.ID_LOOKUP1;
						out_tmp.LABEL_LOOKUP1 = lookup.LABEL_LOOKUP1;
						out = out_tmp;
						// ###############################

					} // end of Var scope

					rejectedInnerJoin_tMapPersistent_1 = false;

					/**
					 * [tMapPersistent_1 main ] stop
					 */
					// Start of branch "out"
					if (out != null) {

						/**
						 * [tFileOutputDelimited_2 main ] start
						 */

						currentComponent = "tFileOutputDelimited_2";

						StringBuilder sb_tFileOutputDelimited_2 = new StringBuilder();

						if (out.ID_MAIN != null) {

							sb_tFileOutputDelimited_2.append(

							out.ID_MAIN

							);

						}

						sb_tFileOutputDelimited_2
								.append(OUT_DELIM_tFileOutputDelimited_2);

						if (out.LABEL_MAIN != null) {

							sb_tFileOutputDelimited_2.append(

							out.LABEL_MAIN

							);

						}

						sb_tFileOutputDelimited_2
								.append(OUT_DELIM_tFileOutputDelimited_2);

						if (out.ID_LOOKUP1 != null) {

							sb_tFileOutputDelimited_2.append(

							out.ID_LOOKUP1

							);

						}

						sb_tFileOutputDelimited_2
								.append(OUT_DELIM_tFileOutputDelimited_2);

						if (out.LABEL_LOOKUP1 != null) {

							sb_tFileOutputDelimited_2.append(

							out.LABEL_LOOKUP1

							);

						}

						sb_tFileOutputDelimited_2
								.append(OUT_DELIM_ROWSEP_tFileOutputDelimited_2);

						outtFileOutputDelimited_2
								.write(sb_tFileOutputDelimited_2.toString());

						nb_line_tFileOutputDelimited_2++;

						/**
						 * [tFileOutputDelimited_2 main ] stop
						 */

					} // End of branch "out"

				} // End of branch "main"

				/**
				 * [tFileInputDelimited_8 end ] start
				 */

				currentComponent = "tFileInputDelimited_8";

			}
			fid_tFileInputDelimited_8.close();
			globalMap.put("tFileInputDelimited_8_NB_LINE",
					fid_tFileInputDelimited_8.getRowNumber());

			ok_Hash.put("tFileInputDelimited_8", true);
			end_Hash.put("tFileInputDelimited_8", System.currentTimeMillis());

			/**
			 * [tFileInputDelimited_8 end ] stop
			 */
			/**
			 * [tMapPersistent_1 end ] start
			 */

			currentComponent = "tMapPersistent_1";

			// ###############################
			// # Lookup hashes releasing
			fsi_main.endGet();

			tHash_Lookup_lookup.endGet();

			globalMap.remove("tHash_Lookup_lookup");

			// ###############################

			ok_Hash.put("tMapPersistent_1", true);
			end_Hash.put("tMapPersistent_1", System.currentTimeMillis());

			/**
			 * [tMapPersistent_1 end ] stop
			 */
			/**
			 * [tFileOutputDelimited_2 end ] start
			 */

			currentComponent = "tFileOutputDelimited_2";

			outtFileOutputDelimited_2.close();
			globalMap.put("tFileOutputDelimited_2_NB_LINE",
					nb_line_tFileOutputDelimited_2);

			ok_Hash.put("tFileOutputDelimited_2", true);
			end_Hash.put("tFileOutputDelimited_2", System.currentTimeMillis());

			/**
			 * [tFileOutputDelimited_2 end ] stop
			 */

			tFileCompare_1Process();

		} catch (Exception e) {

			throw new TalendException(this, e);
		}

		finally {
			// free memory for "tMapPersistent_1"
			globalMap.put("tHash_Lookup_lookup", null);
			System.gc();
		}

	}

	private class lookupStruct implements
			routines.system.IPersistableLookupRow<lookupStruct>,
			Comparable<lookupStruct> {
		private static final int DEFAULT_HASHCODE = 1;
		private static final int PRIME = 31;
		private int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		Integer ID_LOOKUP1;

		String LABEL_LOOKUP1;

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime
						* result
						+ ((this.ID_LOOKUP1 == null) ? 0 : this.ID_LOOKUP1
								.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final lookupStruct other = (lookupStruct) obj;

			if (this.ID_LOOKUP1 == null) {
				if (other.ID_LOOKUP1 != null)
					return false;
			} else if (!this.ID_LOOKUP1.equals(other.ID_LOOKUP1))
				return false;

			return true;
		}

		public void copyDataTo(lookupStruct other) {

			other.ID_LOOKUP1 = this.ID_LOOKUP1;
			other.LABEL_LOOKUP1 = this.LABEL_LOOKUP1;

		}

		public void copyKeysDataTo(lookupStruct other) {

			other.ID_LOOKUP1 = this.ID_LOOKUP1;

		}

		public void loadKeysData(byte[] data) {

			ByteArrayInputStream bai = null;
			ObjectInputStream dis = null;
			byte[] byteArray = null;
			try {
				bai = new ByteArrayInputStream(data);
				dis = new ObjectInputStream(bai);

				int length = 0;

				length = dis.readByte();
				if (length == -1) {
					this.ID_LOOKUP1 = null;
				} else {
					this.ID_LOOKUP1 = dis.readInt();
				}

			} catch (IOException e) {
				throw new RuntimeException(e);

			} finally {
				if (dis != null) {
					try {
						dis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		public byte[] toKeysData() {
			ByteArrayOutputStream bao = null;
			ObjectOutputStream dos = null;
			byte[] byteArray = null;
			try {
				bao = new ByteArrayOutputStream();
				dos = new ObjectOutputStream(bao);

				if (this.ID_LOOKUP1 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeInt(this.ID_LOOKUP1);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				if (dos != null) {
					try {
						dos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return bao.toByteArray();
		}

		/**
		 * Fill Values data from the given byte array.
		 */
		public void loadValuesData(byte[] data) {
			ByteArrayInputStream bai = null;
			ObjectInputStream dis = null;
			byte[] byteArray = null;
			try {
				bai = new ByteArrayInputStream(data);
				dis = new ObjectInputStream(bai);

				int length = 0;

				length = dis.readInt();
				if (length == -1) {
					this.LABEL_LOOKUP1 = null;
				} else {
					byteArray = new byte[length];
					dis.read(byteArray);
					this.LABEL_LOOKUP1 = new String(byteArray);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);

			} finally {
				if (dis != null) {
					try {
						dis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public byte[] toValuesData() {
			ByteArrayOutputStream bao = null;
			ObjectOutputStream dos = null;
			byte[] byteArray = null;
			try {
				bao = new ByteArrayOutputStream();
				dos = new ObjectOutputStream(bao);

				if (this.LABEL_LOOKUP1 == null) {
					dos.writeInt(-1);
				} else {
					byteArray = this.LABEL_LOOKUP1.getBytes();
					dos.writeInt(byteArray.length);
					dos.write(byteArray);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				if (dos != null) {
					try {
						dos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return bao.toByteArray();
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");

			sb.append("ID_LOOKUP1");
			sb.append("=");
			sb.append(String.valueOf(this.ID_LOOKUP1));

			sb.append(", ");

			sb.append("LABEL_LOOKUP1");
			sb.append("=");
			sb.append(String.valueOf(this.LABEL_LOOKUP1));

			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(lookupStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.ID_LOOKUP1,
					other.ID_LOOKUP1);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2
						.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			// if (this.ignoreCase) {
			return string1.compareToIgnoreCase(string2);
			// } else {
			// return string1.compareTo(string2);
			// }
		}

	}

	public void tFileInputDelimited_9Process() throws TalendException {
		try {

			lookupStruct lookup = new lookupStruct();

			/**
			 * [tAdvancedHashPersistent_lookup begin ] start
			 */

			ok_Hash.put("tAdvancedHashPersistent_lookup", false);
			start_Hash.put("tAdvancedHashPersistent_lookup", System
					.currentTimeMillis());
			currentComponent = "tAdvancedHashPersistent_lookup";

			org.talend.designer.components.commons.AdvancedLookup.MATCHING_MODE matchingModeEnum_lookup = org.talend.designer.components.commons.AdvancedLookup.MATCHING_MODE.FIRST_MATCH;

			PersistentSortedAdvancedLookup<lookupStruct> tHash_Lookup_lookup = new PersistentSortedAdvancedLookup<lookupStruct>(
					matchingModeEnum_lookup,
					"/home/amaumont/data/dev/eclipse/workspaces/runtime-talend.product2"
							+ "/Lookup_lookup_") {
				public lookupStruct createRowInstance() {
					return new lookupStruct();
				}
			};

			tHash_Lookup_lookup.initPut();

			globalMap.put("tHash_Lookup_lookup", tHash_Lookup_lookup);

			/**
			 * [tAdvancedHashPersistent_lookup begin ] stop
			 */

			/**
			 * [tFileInputDelimited_9 begin ] start
			 */

			ok_Hash.put("tFileInputDelimited_9", false);
			start_Hash.put("tFileInputDelimited_9", System.currentTimeMillis());
			currentComponent = "tFileInputDelimited_9";

			org.talend.fileprocess.FileInputDelimited fid_tFileInputDelimited_9 = new org.talend.fileprocess.FileInputDelimited(
					(String) context.getProperty("data_dir")
							+ context.current_job_dir + "in/lookup_"
							+ context.indexLookup + ".csv", "ISO-8859-15", ";",
					"\n", true, 0, 0, -1, -1);
			while (fid_tFileInputDelimited_9.nextRecord()) {
				lookup = null;
				lookup = null;
				boolean whetherReject_tFileInputDelimited_9 = false;
				lookup = new lookupStruct();
				try {
					String temp_tFileInputDelimited_9 = "";
					temp_tFileInputDelimited_9 = fid_tFileInputDelimited_9.get(
							0).trim();
					if (temp_tFileInputDelimited_9.length() > 0) {
						lookup.ID_LOOKUP1 = ParserUtils
								.parseTo_Integer(temp_tFileInputDelimited_9);
					} else {
						lookup.ID_LOOKUP1 = null;
					}
					lookup.LABEL_LOOKUP1 = fid_tFileInputDelimited_9.get(1)
							.trim();

				} catch (Exception e) {
					whetherReject_tFileInputDelimited_9 = true;
					throw (e);
				}

				/**
				 * [tFileInputDelimited_9 begin ] stop
				 */
				/**
				 * [tFileInputDelimited_9 main ] start
				 */

				currentComponent = "tFileInputDelimited_9";

				/**
				 * [tFileInputDelimited_9 main ] stop
				 */
				// Start of branch "lookup"
				if (lookup != null) {

					/**
					 * [tAdvancedHashPersistent_lookup main ] start
					 */

					currentComponent = "tAdvancedHashPersistent_lookup";

					lookupStruct lookup_HashRow = tHash_Lookup_lookup
							.getNextFreeRow();

					lookup_HashRow.ID_LOOKUP1 = lookup.ID_LOOKUP1;
					lookup_HashRow.LABEL_LOOKUP1 = lookup.LABEL_LOOKUP1;
					tHash_Lookup_lookup.put(lookup_HashRow);

					/**
					 * [tAdvancedHashPersistent_lookup main ] stop
					 */

				} // End of branch "lookup"

				/**
				 * [tFileInputDelimited_9 end ] start
				 */

				currentComponent = "tFileInputDelimited_9";

			}
			fid_tFileInputDelimited_9.close();
			globalMap.put("tFileInputDelimited_9_NB_LINE",
					fid_tFileInputDelimited_9.getRowNumber());

			ok_Hash.put("tFileInputDelimited_9", true);
			end_Hash.put("tFileInputDelimited_9", System.currentTimeMillis());

			/**
			 * [tFileInputDelimited_9 end ] stop
			 */
			/**
			 * [tAdvancedHashPersistent_lookup end ] start
			 */

			currentComponent = "tAdvancedHashPersistent_lookup";

			tHash_Lookup_lookup.endPut();

			ok_Hash.put("tAdvancedHashPersistent_lookup", true);
			end_Hash.put("tAdvancedHashPersistent_lookup", System
					.currentTimeMillis());

			/**
			 * [tAdvancedHashPersistent_lookup end ] stop
			 */

		} catch (Exception e) {

			throw new TalendException(this, e);
		}

	}

	public static boolean watch = false;
	public static int portStats = 3334;
	public static int portTraces = 4334;
	public static String clientHost;
	public static String defaultClientHost = "localhost";
	public static String contextStr = "local";
	public static String pid = "0";
	public static String rootPid = null;
	public static String fatherPid = null;

	private static java.util.Properties context_param = new java.util.Properties();

	public static String status = "";

	public static void main(String[] args) {

		int exitCode = runJobInTOS(args);

		globalBuffer.clear();

		System.exit(exitCode);
	}

	public static String[][] runJob(String[] args) {

		int exitCode = runJobInTOS(args);
		String[][] bufferValue = (String[][]) globalBuffer
				.toArray(new String[globalBuffer.size()][]);

		globalBuffer.clear();

		return bufferValue;
	}

	public static synchronized int runJobInTOS(String[] args) {

		init();

		String lastStr = "";
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--context_param")) {
				lastStr = arg;
			} else if (lastStr.equals("")) {
				evalParam(arg);
			} else {
				evalParam(lastStr + " " + arg);
				lastStr = "";
			}
		}

		if (clientHost == null) {
			clientHost = defaultClientHost;
		}

		pid = TalendString.getAsciiRandomString(6);
		if (rootPid == null) {
			rootPid = pid;
		}
		if (fatherPid == null) {
			fatherPid = pid;
		}

		try {
			java.io.InputStream inContext = tMap_03_persistence_03_child.class
					.getClassLoader()
					.getResourceAsStream(
							"java_project_6/tmap_03_persistence_03_child_0_1/contexts/Default.properties");
			if (inContext != null) {
				defaultProps.load(inContext);
				inContext.close();
				context = new ContextProperties(defaultProps);
			}

			if (contextStr.compareTo("Default") != 0) {
				inContext = tMap_03_persistence_03_child.class.getClassLoader()
						.getResourceAsStream(
								"java_project_6/tmap_03_persistence_03_child_0_1/contexts/"
										+ contextStr + ".properties");
				if (inContext != null) {
					context.load(inContext);
					inContext.close();
				}
			}

			if (!context_param.isEmpty()) {
				context.putAll(context_param);
			}

			context.param_file_path = (String) context
					.getProperty("param_file_path");

			context.data_dir = (String) context.getProperty("data_dir");

			context.data_output_dir = (String) context
					.getProperty("data_output_dir");

			context.result_host = (String) context.getProperty("result_host");

			context.result_port = (String) context.getProperty("result_port");

			context.result_database = (String) context
					.getProperty("result_database");

			context.result_username = (String) context
					.getProperty("result_username");

			context.result_password = (String) context
					.getProperty("result_password");

			context.result_table = (String) context.getProperty("result_table");

			context.mysql_host = (String) context.getProperty("mysql_host");

			context.mysql_port = (String) context.getProperty("mysql_port");

			context.mysql_schema = (String) context.getProperty("mysql_schema");

			context.mysql_username = (String) context
					.getProperty("mysql_username");

			context.mysql_password = (String) context
					.getProperty("mysql_password");

			context.current_job_dir = (String) context
					.getProperty("current_job_dir");

			context.indexMain = (String) context.getProperty("indexMain");

			context.indexLookup = (String) context.getProperty("indexLookup");

		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		long startUsedMemory = Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory();
		long endUsedMemory = 0;
		long end = 0;
		long start = System.currentTimeMillis();
		final tMap_03_persistence_03_child tMap_03_persistence_03_childClass = new tMap_03_persistence_03_child();
		try {
			errorCode = null;
			tMap_03_persistence_03_childClass.tFileInputDelimited_2Process();
			status = "end";
		} catch (TalendException e_tFileInputDelimited_2) {
			status = "failure";
			e_tFileInputDelimited_2.printStackTrace();

		} finally {
		}
		end = System.currentTimeMillis();
		if (watch) {
			System.out.println((end - start) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory();
		if (false) {
			System.out
					.println((endUsedMemory - startUsedMemory)
							+ " bytes memory increase when running : tMap_03_persistence_03_child");
		}

		reset();

		if (errorCode == null) {
			return status != null && status.equals("failure") ? 1 : 0;
		} else {
			return errorCode.intValue();
		}
	}

	public static void evalParam(String arg) {
		if (arg.startsWith("--watch")) {
			watch = true;
		} else if (arg.startsWith("--stat_port=")) {
			portStats = Integer.parseInt(arg.substring(12));
		} else if (arg.startsWith("--trace_port=")) {
			portTraces = Integer.parseInt(arg.substring(13));
		} else if (arg.startsWith("--client_host=")) {
			clientHost = arg.substring(14);
		} else if (arg.startsWith("--context=")) {
			contextStr = arg.substring(10);
		} else if (arg.startsWith("--father_pid=")) {
			fatherPid = arg.substring(13);
		} else if (arg.startsWith("--root_pid=")) {
			rootPid = arg.substring(11);
		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				context_param.put(keyValue.substring(0, index), keyValue
						.substring(index + 1));
			}
		}

	}

	private static void init() {
		errorCode = null;
		status = "";
	}

	private static void reset() {

		defaultProps.clear();
		context.clear();
		currentComponent = "";
		start_Hash.clear();
		end_Hash.clear();
		ok_Hash.clear();
		globalMap.clear();
		watch = false;
		portStats = 3334;
		portTraces = 4334;
		clientHost = null;
		defaultClientHost = "localhost";
		contextStr = "local";
		pid = "0";
		rootPid = null;
		fatherPid = null;
		context_param.clear();
		System.gc();
	}
}
/*******************************************************************************
 * 86996 characters generated by Talend OpenStudio on the 21 avril 2008 13:20:45
 * CEST
 ******************************************************************************/
