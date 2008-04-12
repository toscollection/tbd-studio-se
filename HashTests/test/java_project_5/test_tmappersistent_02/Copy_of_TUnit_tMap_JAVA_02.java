// ============================================================================
package java_project_5.test_tmappersistent_02;

import routines.libs;
import routines.randomGenerator;
import routines.DataOperation;
import routines.Mathematical;
import routines.Numeric;
import routines.Relational;
import routines.StringHandling;
import routines.TalendDataGenerator;
import routines.TalendDate;
import routines.TalendString;
import routines.system.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;

/**
 * Job: Copy_of_TUnit_tMap_JAVA_02 Purpose: <br>
 * Description: Tests ALL_MACTHES/Inner join in lookup1 and LAST_MATCH/No inner
 * join in lookup2 <br>
 * 
 * @author amaumont@talend.com
 * @version 2.2.0.qualifier
 * @status
 */
public class Copy_of_TUnit_tMap_JAVA_02 {
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

		public static String rep_in;
		public static String rep_out;
		public static String rep_ref;
	}

	private static ContextProperties context = new ContextProperties();
	private static final String jobName = "Copy_of_TUnit_tMap_JAVA_02";
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
		private Copy_of_TUnit_tMap_JAVA_02 c = null;

		private TalendException(Copy_of_TUnit_tMap_JAVA_02 c, Exception e) {
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

	public void tRunJob_1_error(Exception exception) throws TalendException {
		end_Hash.put("tRunJob_1", System.currentTimeMillis());
		tRunJob_1_onSubJobError(exception);
	}

	public void tFileInputDelimited_2_error(Exception exception)
			throws TalendException {
		end_Hash.put("tFileInputDelimited_2", System.currentTimeMillis());
		tFileInputDelimited_2_onSubJobError(exception);
	}

	public void tMapPersistent_1_error(Exception exception)
			throws TalendException {
		end_Hash.put("tMapPersistent_1", System.currentTimeMillis());
		tFileInputDelimited_2_onSubJobError(exception);
	}

	public void tFileOutputDelimited_3_error(Exception exception)
			throws TalendException {
		end_Hash.put("tFileOutputDelimited_3", System.currentTimeMillis());
		tFileInputDelimited_2_onSubJobError(exception);
	}

	public void tFileOutputDelimited_2_error(Exception exception)
			throws TalendException {
		end_Hash.put("tFileOutputDelimited_2", System.currentTimeMillis());
		tFileInputDelimited_2_onSubJobError(exception);
	}

	public void tFileOutputDelimited_5_error(Exception exception)
			throws TalendException {
		end_Hash.put("tFileOutputDelimited_5", System.currentTimeMillis());
		tFileInputDelimited_2_onSubJobError(exception);
	}

	public void tFileOutputDelimited_1_error(Exception exception)
			throws TalendException {
		end_Hash.put("tFileOutputDelimited_1", System.currentTimeMillis());
		tFileInputDelimited_2_onSubJobError(exception);
	}

	public void tFileOutputDelimited_6_error(Exception exception)
			throws TalendException {
		end_Hash.put("tFileOutputDelimited_6", System.currentTimeMillis());
		tFileInputDelimited_2_onSubJobError(exception);
	}

	public void tFileCompare_1_error(Exception exception)
			throws TalendException {
		end_Hash.put("tFileCompare_1", System.currentTimeMillis());
		tFileCompare_1_onSubJobError(exception);
	}

	public void tFileCompare_2_error(Exception exception)
			throws TalendException {
		end_Hash.put("tFileCompare_2", System.currentTimeMillis());
		tFileCompare_2_onSubJobError(exception);
	}

	public void tFileCompare_5_error(Exception exception)
			throws TalendException {
		end_Hash.put("tFileCompare_5", System.currentTimeMillis());
		tFileCompare_5_onSubJobError(exception);
	}

	public void tFileCompare_3_error(Exception exception)
			throws TalendException {
		end_Hash.put("tFileCompare_3", System.currentTimeMillis());
		tFileCompare_3_onSubJobError(exception);
	}

	public void tFileCompare_6_error(Exception exception)
			throws TalendException {
		end_Hash.put("tFileCompare_6", System.currentTimeMillis());
		tFileCompare_6_onSubJobError(exception);
	}

	public void tFileInputDelimited_5_error(Exception exception)
			throws TalendException {
		end_Hash.put("tFileInputDelimited_5", System.currentTimeMillis());
		tFileInputDelimited_2_onSubJobError(exception);
	}

	public void tFileInputDelimited_6_error(Exception exception)
			throws TalendException {
		end_Hash.put("tFileInputDelimited_6", System.currentTimeMillis());
		tFileInputDelimited_2_onSubJobError(exception);
	}

	public void tAdvancedHashPersistent_row2_error(Exception exception)
			throws TalendException {
		end_Hash
				.put("tAdvancedHashPersistent_row2", System.currentTimeMillis());
		tFileInputDelimited_2_onSubJobError(exception);
	}

	public void tAdvancedHashPersistent_row3_error(Exception exception)
			throws TalendException {
		end_Hash
				.put("tAdvancedHashPersistent_row3", System.currentTimeMillis());
		tFileInputDelimited_2_onSubJobError(exception);
	}

	public void tRunJob_1_onSubJobError(Exception exception)
			throws TalendException {
	}

	public void tFileInputDelimited_2_onSubJobError(Exception exception)
			throws TalendException {
	}

	public void tFileCompare_1_onSubJobError(Exception exception)
			throws TalendException {
	}

	public void tFileCompare_2_onSubJobError(Exception exception)
			throws TalendException {
	}

	public void tFileCompare_5_onSubJobError(Exception exception)
			throws TalendException {
	}

	public void tFileCompare_3_onSubJobError(Exception exception)
			throws TalendException {
	}

	public void tFileCompare_6_onSubJobError(Exception exception)
			throws TalendException {
	}

	public void tRunJob_1Process() throws TalendException {
		try {

			/**
			 * [tRunJob_1 begin ] start
			 */

			ok_Hash.put("tRunJob_1", false);
			start_Hash.put("tRunJob_1", System.currentTimeMillis());
			currentComponent = "tRunJob_1";

			/**
			 * [tRunJob_1 begin ] stop
			 */
			/**
			 * [tRunJob_1 main ] start
			 */

			currentComponent = "tRunJob_1";

			java.util.List<String> paraList_tRunJob_1 = new java.util.ArrayList<String>();

			paraList_tRunJob_1.add("--father_pid=" + pid);

			paraList_tRunJob_1.add("--root_pid=" + rootPid);

			paraList_tRunJob_1.add("--context=Default");

			paraList_tRunJob_1.add("--context_param directory="
					+ (String) ((String) context.getProperty("new1")));

			String[][] childReturn_tRunJob_1 = java_project_5.delete_all_root_and_subdir_files.delete_all_root_and_subdir_files0_1.runJob((String[]) paraList_tRunJob_1
							.toArray(new String[paraList_tRunJob_1.size()]));

			errorCode = java_project_5.delete_all_root_and_subdir_files.delete_all_root_and_subdir_files0_1.errorCode;

			if (java_project_5.delete_all_root_and_subdir_files.delete_all_root_and_subdir_files0_1.errorCode != null
					|| java_project_5.delete_all_root_and_subdir_files.delete_all_root_and_subdir_files0_1.status
							.equals("failure")) {
				throw new RuntimeException("Child job running failed");
			}

			/**
			 * [tRunJob_1 main ] stop
			 */
			/**
			 * [tRunJob_1 end ] start
			 */

			currentComponent = "tRunJob_1";

			ok_Hash.put("tRunJob_1", true);
			end_Hash.put("tRunJob_1", System.currentTimeMillis());

			/**
			 * [tRunJob_1 end ] stop
			 */
			tFileInputDelimited_2Process();

		} catch (Exception e) {

			throw new TalendException(this, e);
		}

	}

	private class OUTStruct implements
			routines.system.IPersistableRow<OUTStruct>, Comparable<OUTStruct> {

		Integer ID_MAIN;

		String LABEL_MAIN;

		Integer ID_LOOKUP1;

		String LABEL_LOOKUP1;

		Integer ID_LOOKUP2;

		String LABEL_LOOKUP2;

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

				length = dis.readByte();
				if (length == -1) {
					this.ID_LOOKUP2 = null;
				} else {
					this.ID_LOOKUP2 = dis.readInt();
				}

				length = dis.readInt();
				if (length == -1) {
					this.LABEL_LOOKUP2 = null;
				} else {
					byteArray = new byte[length];
					dis.read(byteArray);
					this.LABEL_LOOKUP2 = new String(byteArray);
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

				if (this.ID_LOOKUP2 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeInt(this.ID_LOOKUP2);
				}

				if (this.LABEL_LOOKUP2 == null) {
					dos.writeInt(-1);
				} else {
					byteArray = this.LABEL_LOOKUP2.getBytes();
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

			sb.append(", ");

			sb.append("ID_LOOKUP2");
			sb.append("=");
			sb.append(String.valueOf(this.ID_LOOKUP2));

			sb.append(", ");

			sb.append("LABEL_LOOKUP2");
			sb.append("=");
			sb.append(String.valueOf(this.LABEL_LOOKUP2));

			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(OUTStruct other) {

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

	private class REJECTStruct implements
			routines.system.IPersistableRow<REJECTStruct>,
			Comparable<REJECTStruct> {

		String newColumn;

		Integer ID_MAIN;

		String LABEL_MAIN;

		Integer ID_LOOKUP1;

		String LABEL_LOOKUP1;

		Integer ID_LOOKUP2;

		String LABEL_LOOKUP2;

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
					this.newColumn = null;
				} else {
					byteArray = new byte[length];
					dis.read(byteArray);
					this.newColumn = new String(byteArray);
				}

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

				length = dis.readByte();
				if (length == -1) {
					this.ID_LOOKUP2 = null;
				} else {
					this.ID_LOOKUP2 = dis.readInt();
				}

				length = dis.readInt();
				if (length == -1) {
					this.LABEL_LOOKUP2 = null;
				} else {
					byteArray = new byte[length];
					dis.read(byteArray);
					this.LABEL_LOOKUP2 = new String(byteArray);
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

				if (this.newColumn == null) {
					dos.writeInt(-1);
				} else {
					byteArray = this.newColumn.getBytes();
					dos.writeInt(byteArray.length);
					dos.write(byteArray);
				}

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

				if (this.ID_LOOKUP2 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeInt(this.ID_LOOKUP2);
				}

				if (this.LABEL_LOOKUP2 == null) {
					dos.writeInt(-1);
				} else {
					byteArray = this.LABEL_LOOKUP2.getBytes();
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

			sb.append("newColumn");
			sb.append("=");
			sb.append(String.valueOf(this.newColumn));

			sb.append(", ");

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

			sb.append(", ");

			sb.append("ID_LOOKUP2");
			sb.append("=");
			sb.append(String.valueOf(this.ID_LOOKUP2));

			sb.append(", ");

			sb.append("LABEL_LOOKUP2");
			sb.append("=");
			sb.append(String.valueOf(this.LABEL_LOOKUP2));

			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(REJECTStruct other) {

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

	private class REJECT_WITH_FILTERStruct implements
			routines.system.IPersistableRow<REJECT_WITH_FILTERStruct>,
			Comparable<REJECT_WITH_FILTERStruct> {

		String newColumn;

		Integer ID_MAIN;

		String LABEL_MAIN;

		Integer ID_LOOKUP1;

		String LABEL_LOOKUP1;

		Integer ID_LOOKUP2;

		String LABEL_LOOKUP2;

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
					this.newColumn = null;
				} else {
					byteArray = new byte[length];
					dis.read(byteArray);
					this.newColumn = new String(byteArray);
				}

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

				length = dis.readByte();
				if (length == -1) {
					this.ID_LOOKUP2 = null;
				} else {
					this.ID_LOOKUP2 = dis.readInt();
				}

				length = dis.readInt();
				if (length == -1) {
					this.LABEL_LOOKUP2 = null;
				} else {
					byteArray = new byte[length];
					dis.read(byteArray);
					this.LABEL_LOOKUP2 = new String(byteArray);
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

				if (this.newColumn == null) {
					dos.writeInt(-1);
				} else {
					byteArray = this.newColumn.getBytes();
					dos.writeInt(byteArray.length);
					dos.write(byteArray);
				}

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

				if (this.ID_LOOKUP2 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeInt(this.ID_LOOKUP2);
				}

				if (this.LABEL_LOOKUP2 == null) {
					dos.writeInt(-1);
				} else {
					byteArray = this.LABEL_LOOKUP2.getBytes();
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

			sb.append("newColumn");
			sb.append("=");
			sb.append(String.valueOf(this.newColumn));

			sb.append(", ");

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

			sb.append(", ");

			sb.append("ID_LOOKUP2");
			sb.append("=");
			sb.append(String.valueOf(this.ID_LOOKUP2));

			sb.append(", ");

			sb.append("LABEL_LOOKUP2");
			sb.append("=");
			sb.append(String.valueOf(this.LABEL_LOOKUP2));

			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(REJECT_WITH_FILTERStruct other) {

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

	private class REJECT_INNER_JOINStruct implements
			routines.system.IPersistableRow<REJECT_INNER_JOINStruct>,
			Comparable<REJECT_INNER_JOINStruct> {

		String newColumn;

		Integer ID_MAIN;

		String LABEL_MAIN;

		Integer ID_LOOKUP1;

		String LABEL_LOOKUP1;

		Integer ID_LOOKUP2;

		String LABEL_LOOKUP2;

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
					this.newColumn = null;
				} else {
					byteArray = new byte[length];
					dis.read(byteArray);
					this.newColumn = new String(byteArray);
				}

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

				length = dis.readByte();
				if (length == -1) {
					this.ID_LOOKUP2 = null;
				} else {
					this.ID_LOOKUP2 = dis.readInt();
				}

				length = dis.readInt();
				if (length == -1) {
					this.LABEL_LOOKUP2 = null;
				} else {
					byteArray = new byte[length];
					dis.read(byteArray);
					this.LABEL_LOOKUP2 = new String(byteArray);
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

				if (this.newColumn == null) {
					dos.writeInt(-1);
				} else {
					byteArray = this.newColumn.getBytes();
					dos.writeInt(byteArray.length);
					dos.write(byteArray);
				}

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

				if (this.ID_LOOKUP2 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeInt(this.ID_LOOKUP2);
				}

				if (this.LABEL_LOOKUP2 == null) {
					dos.writeInt(-1);
				} else {
					byteArray = this.LABEL_LOOKUP2.getBytes();
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

			sb.append("newColumn");
			sb.append("=");
			sb.append(String.valueOf(this.newColumn));

			sb.append(", ");

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

			sb.append(", ");

			sb.append("ID_LOOKUP2");
			sb.append("=");
			sb.append(String.valueOf(this.ID_LOOKUP2));

			sb.append(", ");

			sb.append("LABEL_LOOKUP2");
			sb.append("=");
			sb.append(String.valueOf(this.LABEL_LOOKUP2));

			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(REJECT_INNER_JOINStruct other) {

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

	private class REJECT_INNER_JOIN_WITH_FILTERStruct
			implements
			routines.system.IPersistableRow<REJECT_INNER_JOIN_WITH_FILTERStruct>,
			Comparable<REJECT_INNER_JOIN_WITH_FILTERStruct> {

		String newColumn;

		Integer ID_MAIN;

		String LABEL_MAIN;

		Integer ID_LOOKUP1;

		String LABEL_LOOKUP1;

		Integer ID_LOOKUP2;

		String LABEL_LOOKUP2;

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
					this.newColumn = null;
				} else {
					byteArray = new byte[length];
					dis.read(byteArray);
					this.newColumn = new String(byteArray);
				}

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

				length = dis.readByte();
				if (length == -1) {
					this.ID_LOOKUP2 = null;
				} else {
					this.ID_LOOKUP2 = dis.readInt();
				}

				length = dis.readInt();
				if (length == -1) {
					this.LABEL_LOOKUP2 = null;
				} else {
					byteArray = new byte[length];
					dis.read(byteArray);
					this.LABEL_LOOKUP2 = new String(byteArray);
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

				if (this.newColumn == null) {
					dos.writeInt(-1);
				} else {
					byteArray = this.newColumn.getBytes();
					dos.writeInt(byteArray.length);
					dos.write(byteArray);
				}

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

				if (this.ID_LOOKUP2 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeInt(this.ID_LOOKUP2);
				}

				if (this.LABEL_LOOKUP2 == null) {
					dos.writeInt(-1);
				} else {
					byteArray = this.LABEL_LOOKUP2.getBytes();
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

			sb.append("newColumn");
			sb.append("=");
			sb.append(String.valueOf(this.newColumn));

			sb.append(", ");

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

			sb.append(", ");

			sb.append("ID_LOOKUP2");
			sb.append("=");
			sb.append(String.valueOf(this.ID_LOOKUP2));

			sb.append(", ");

			sb.append("LABEL_LOOKUP2");
			sb.append("=");
			sb.append(String.valueOf(this.LABEL_LOOKUP2));

			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(REJECT_INNER_JOIN_WITH_FILTERStruct other) {

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

	private class row1Struct implements
			routines.system.IPersistableRow<row1Struct>, Comparable<row1Struct> {

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
		public int compareTo(row1Struct other) {

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

	private class after_tFileInputDelimited_2Struct implements
			routines.system.IPersistableRow<after_tFileInputDelimited_2Struct>,
			Comparable<after_tFileInputDelimited_2Struct> {

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
		public int compareTo(after_tFileInputDelimited_2Struct other) {

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
			tFileInputDelimited_5Process();
			tFileInputDelimited_6Process();

			row1Struct row1 = new row1Struct();
			OUTStruct OUT = new OUTStruct();
			REJECTStruct REJECT = new REJECTStruct();
			REJECT_WITH_FILTERStruct REJECT_WITH_FILTER = new REJECT_WITH_FILTERStruct();
			REJECT_INNER_JOINStruct REJECT_INNER_JOIN = new REJECT_INNER_JOINStruct();
			REJECT_INNER_JOIN_WITH_FILTERStruct REJECT_INNER_JOIN_WITH_FILTER = new REJECT_INNER_JOIN_WITH_FILTERStruct();

			/**
			 * [tFileOutputDelimited_3 begin ] start
			 */

			ok_Hash.put("tFileOutputDelimited_3", false);
			start_Hash
					.put("tFileOutputDelimited_3", System.currentTimeMillis());
			currentComponent = "tFileOutputDelimited_3";

			String fileName_tFileOutputDelimited_3 = (new java.io.File(
					(java.lang.String) ((String) context.getProperty("rep_out"))
							+ "out_main.csv")).getAbsolutePath().replace("\\",
					"/");
			String fullName_tFileOutputDelimited_3 = null;
			String extension_tFileOutputDelimited_3 = null;
			String directory_tFileOutputDelimited_3 = null;
			if ((fileName_tFileOutputDelimited_3.indexOf("/") != -1)) {
				if (fileName_tFileOutputDelimited_3.lastIndexOf(".") < fileName_tFileOutputDelimited_3
						.lastIndexOf("/")) {
					fullName_tFileOutputDelimited_3 = fileName_tFileOutputDelimited_3;
					extension_tFileOutputDelimited_3 = "";
				} else {
					fullName_tFileOutputDelimited_3 = fileName_tFileOutputDelimited_3
							.substring(0, fileName_tFileOutputDelimited_3
									.lastIndexOf("."));
					extension_tFileOutputDelimited_3 = fileName_tFileOutputDelimited_3
							.substring(fileName_tFileOutputDelimited_3
									.lastIndexOf("."));
				}
				directory_tFileOutputDelimited_3 = fileName_tFileOutputDelimited_3
						.substring(0, fileName_tFileOutputDelimited_3
								.lastIndexOf("/"));
			} else {
				if (fileName_tFileOutputDelimited_3.lastIndexOf(".") != -1) {
					fullName_tFileOutputDelimited_3 = fileName_tFileOutputDelimited_3
							.substring(0, fileName_tFileOutputDelimited_3
									.lastIndexOf("."));
					extension_tFileOutputDelimited_3 = fileName_tFileOutputDelimited_3
							.substring(fileName_tFileOutputDelimited_3
									.lastIndexOf("."));
				} else {
					fullName_tFileOutputDelimited_3 = fileName_tFileOutputDelimited_3;
					extension_tFileOutputDelimited_3 = "";
				}
				directory_tFileOutputDelimited_3 = "";
			}

			int nb_line_tFileOutputDelimited_3 = 0;
			int splitEvery_tFileOutputDelimited_3 = 1000;
			int splitedFileNo_tFileOutputDelimited_3 = 0;
			int currentRow_tFileOutputDelimited_3 = 0;

			final String OUT_DELIM_tFileOutputDelimited_3 = /**
															 * Start field
															 * tFileOutputDelimited_3:FIELDSEPARATOR
															 */
			";"/** End field tFileOutputDelimited_3:FIELDSEPARATOR */
			;

			final String OUT_DELIM_ROWSEP_tFileOutputDelimited_3 = /**
																	 * Start
																	 * field
																	 * tFileOutputDelimited_3:ROWSEPARATOR
																	 */
			"\n"/** End field tFileOutputDelimited_3:ROWSEPARATOR */
			;

			// create directory only if not exists
			if (directory_tFileOutputDelimited_3 != null
					&& directory_tFileOutputDelimited_3.trim().length() != 0) {
				java.io.File dir_tFileOutputDelimited_3 = new java.io.File(
						directory_tFileOutputDelimited_3);
				if (!dir_tFileOutputDelimited_3.exists()) {
					dir_tFileOutputDelimited_3.mkdirs();
				}
			}

			java.io.BufferedWriter outtFileOutputDelimited_3 = new java.io.BufferedWriter(
					new java.io.OutputStreamWriter(
							new java.io.FileOutputStream(
									fileName_tFileOutputDelimited_3, false),
							"ISO-8859-15"));
			java.io.File filetFileOutputDelimited_3 = new java.io.File(
					fileName_tFileOutputDelimited_3);

			/**
			 * [tFileOutputDelimited_3 begin ] stop
			 */

			/**
			 * [tFileOutputDelimited_2 begin ] start
			 */

			ok_Hash.put("tFileOutputDelimited_2", false);
			start_Hash
					.put("tFileOutputDelimited_2", System.currentTimeMillis());
			currentComponent = "tFileOutputDelimited_2";

			String fileName_tFileOutputDelimited_2 = (new java.io.File(
					(java.lang.String) ((String) context.getProperty("rep_out"))
							+ "out_reject.csv")).getAbsolutePath().replace(
					"\\", "/");
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

			/**
			 * [tFileOutputDelimited_2 begin ] stop
			 */

			/**
			 * [tFileOutputDelimited_5 begin ] start
			 */

			ok_Hash.put("tFileOutputDelimited_5", false);
			start_Hash
					.put("tFileOutputDelimited_5", System.currentTimeMillis());
			currentComponent = "tFileOutputDelimited_5";

			String fileName_tFileOutputDelimited_5 = (new java.io.File(
					(java.lang.String) ((String) context.getProperty("rep_out"))
							+ "out_reject_with_filter.csv")).getAbsolutePath()
					.replace("\\", "/");
			String fullName_tFileOutputDelimited_5 = null;
			String extension_tFileOutputDelimited_5 = null;
			String directory_tFileOutputDelimited_5 = null;
			if ((fileName_tFileOutputDelimited_5.indexOf("/") != -1)) {
				if (fileName_tFileOutputDelimited_5.lastIndexOf(".") < fileName_tFileOutputDelimited_5
						.lastIndexOf("/")) {
					fullName_tFileOutputDelimited_5 = fileName_tFileOutputDelimited_5;
					extension_tFileOutputDelimited_5 = "";
				} else {
					fullName_tFileOutputDelimited_5 = fileName_tFileOutputDelimited_5
							.substring(0, fileName_tFileOutputDelimited_5
									.lastIndexOf("."));
					extension_tFileOutputDelimited_5 = fileName_tFileOutputDelimited_5
							.substring(fileName_tFileOutputDelimited_5
									.lastIndexOf("."));
				}
				directory_tFileOutputDelimited_5 = fileName_tFileOutputDelimited_5
						.substring(0, fileName_tFileOutputDelimited_5
								.lastIndexOf("/"));
			} else {
				if (fileName_tFileOutputDelimited_5.lastIndexOf(".") != -1) {
					fullName_tFileOutputDelimited_5 = fileName_tFileOutputDelimited_5
							.substring(0, fileName_tFileOutputDelimited_5
									.lastIndexOf("."));
					extension_tFileOutputDelimited_5 = fileName_tFileOutputDelimited_5
							.substring(fileName_tFileOutputDelimited_5
									.lastIndexOf("."));
				} else {
					fullName_tFileOutputDelimited_5 = fileName_tFileOutputDelimited_5;
					extension_tFileOutputDelimited_5 = "";
				}
				directory_tFileOutputDelimited_5 = "";
			}

			int nb_line_tFileOutputDelimited_5 = 0;
			int splitEvery_tFileOutputDelimited_5 = 1000;
			int splitedFileNo_tFileOutputDelimited_5 = 0;
			int currentRow_tFileOutputDelimited_5 = 0;

			final String OUT_DELIM_tFileOutputDelimited_5 = /**
															 * Start field
															 * tFileOutputDelimited_5:FIELDSEPARATOR
															 */
			";"/** End field tFileOutputDelimited_5:FIELDSEPARATOR */
			;

			final String OUT_DELIM_ROWSEP_tFileOutputDelimited_5 = /**
																	 * Start
																	 * field
																	 * tFileOutputDelimited_5:ROWSEPARATOR
																	 */
			"\n"/** End field tFileOutputDelimited_5:ROWSEPARATOR */
			;

			// create directory only if not exists
			if (directory_tFileOutputDelimited_5 != null
					&& directory_tFileOutputDelimited_5.trim().length() != 0) {
				java.io.File dir_tFileOutputDelimited_5 = new java.io.File(
						directory_tFileOutputDelimited_5);
				if (!dir_tFileOutputDelimited_5.exists()) {
					dir_tFileOutputDelimited_5.mkdirs();
				}
			}

			java.io.BufferedWriter outtFileOutputDelimited_5 = new java.io.BufferedWriter(
					new java.io.OutputStreamWriter(
							new java.io.FileOutputStream(
									fileName_tFileOutputDelimited_5, false),
							"ISO-8859-15"));
			java.io.File filetFileOutputDelimited_5 = new java.io.File(
					fileName_tFileOutputDelimited_5);

			/**
			 * [tFileOutputDelimited_5 begin ] stop
			 */

			/**
			 * [tFileOutputDelimited_1 begin ] start
			 */

			ok_Hash.put("tFileOutputDelimited_1", false);
			start_Hash
					.put("tFileOutputDelimited_1", System.currentTimeMillis());
			currentComponent = "tFileOutputDelimited_1";

			String fileName_tFileOutputDelimited_1 = (new java.io.File(
					(java.lang.String) ((String) context.getProperty("rep_out"))
							+ "out_reject_inner_join.csv")).getAbsolutePath()
					.replace("\\", "/");
			String fullName_tFileOutputDelimited_1 = null;
			String extension_tFileOutputDelimited_1 = null;
			String directory_tFileOutputDelimited_1 = null;
			if ((fileName_tFileOutputDelimited_1.indexOf("/") != -1)) {
				if (fileName_tFileOutputDelimited_1.lastIndexOf(".") < fileName_tFileOutputDelimited_1
						.lastIndexOf("/")) {
					fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1;
					extension_tFileOutputDelimited_1 = "";
				} else {
					fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
							.substring(0, fileName_tFileOutputDelimited_1
									.lastIndexOf("."));
					extension_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
							.substring(fileName_tFileOutputDelimited_1
									.lastIndexOf("."));
				}
				directory_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
						.substring(0, fileName_tFileOutputDelimited_1
								.lastIndexOf("/"));
			} else {
				if (fileName_tFileOutputDelimited_1.lastIndexOf(".") != -1) {
					fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
							.substring(0, fileName_tFileOutputDelimited_1
									.lastIndexOf("."));
					extension_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
							.substring(fileName_tFileOutputDelimited_1
									.lastIndexOf("."));
				} else {
					fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1;
					extension_tFileOutputDelimited_1 = "";
				}
				directory_tFileOutputDelimited_1 = "";
			}

			int nb_line_tFileOutputDelimited_1 = 0;
			int splitEvery_tFileOutputDelimited_1 = 1000;
			int splitedFileNo_tFileOutputDelimited_1 = 0;
			int currentRow_tFileOutputDelimited_1 = 0;

			final String OUT_DELIM_tFileOutputDelimited_1 = /**
															 * Start field
															 * tFileOutputDelimited_1:FIELDSEPARATOR
															 */
			";"/** End field tFileOutputDelimited_1:FIELDSEPARATOR */
			;

			final String OUT_DELIM_ROWSEP_tFileOutputDelimited_1 = /**
																	 * Start
																	 * field
																	 * tFileOutputDelimited_1:ROWSEPARATOR
																	 */
			"\n"/** End field tFileOutputDelimited_1:ROWSEPARATOR */
			;

			// create directory only if not exists
			if (directory_tFileOutputDelimited_1 != null
					&& directory_tFileOutputDelimited_1.trim().length() != 0) {
				java.io.File dir_tFileOutputDelimited_1 = new java.io.File(
						directory_tFileOutputDelimited_1);
				if (!dir_tFileOutputDelimited_1.exists()) {
					dir_tFileOutputDelimited_1.mkdirs();
				}
			}

			java.io.BufferedWriter outtFileOutputDelimited_1 = new java.io.BufferedWriter(
					new java.io.OutputStreamWriter(
							new java.io.FileOutputStream(
									fileName_tFileOutputDelimited_1, false),
							"ISO-8859-15"));
			java.io.File filetFileOutputDelimited_1 = new java.io.File(
					fileName_tFileOutputDelimited_1);

			/**
			 * [tFileOutputDelimited_1 begin ] stop
			 */

			/**
			 * [tFileOutputDelimited_6 begin ] start
			 */

			ok_Hash.put("tFileOutputDelimited_6", false);
			start_Hash
					.put("tFileOutputDelimited_6", System.currentTimeMillis());
			currentComponent = "tFileOutputDelimited_6";

			String fileName_tFileOutputDelimited_6 = (new java.io.File(
					(java.lang.String) ((String) context.getProperty("rep_out"))
							+ "out_reject_inner_join_with_filter.csv"))
					.getAbsolutePath().replace("\\", "/");
			String fullName_tFileOutputDelimited_6 = null;
			String extension_tFileOutputDelimited_6 = null;
			String directory_tFileOutputDelimited_6 = null;
			if ((fileName_tFileOutputDelimited_6.indexOf("/") != -1)) {
				if (fileName_tFileOutputDelimited_6.lastIndexOf(".") < fileName_tFileOutputDelimited_6
						.lastIndexOf("/")) {
					fullName_tFileOutputDelimited_6 = fileName_tFileOutputDelimited_6;
					extension_tFileOutputDelimited_6 = "";
				} else {
					fullName_tFileOutputDelimited_6 = fileName_tFileOutputDelimited_6
							.substring(0, fileName_tFileOutputDelimited_6
									.lastIndexOf("."));
					extension_tFileOutputDelimited_6 = fileName_tFileOutputDelimited_6
							.substring(fileName_tFileOutputDelimited_6
									.lastIndexOf("."));
				}
				directory_tFileOutputDelimited_6 = fileName_tFileOutputDelimited_6
						.substring(0, fileName_tFileOutputDelimited_6
								.lastIndexOf("/"));
			} else {
				if (fileName_tFileOutputDelimited_6.lastIndexOf(".") != -1) {
					fullName_tFileOutputDelimited_6 = fileName_tFileOutputDelimited_6
							.substring(0, fileName_tFileOutputDelimited_6
									.lastIndexOf("."));
					extension_tFileOutputDelimited_6 = fileName_tFileOutputDelimited_6
							.substring(fileName_tFileOutputDelimited_6
									.lastIndexOf("."));
				} else {
					fullName_tFileOutputDelimited_6 = fileName_tFileOutputDelimited_6;
					extension_tFileOutputDelimited_6 = "";
				}
				directory_tFileOutputDelimited_6 = "";
			}

			int nb_line_tFileOutputDelimited_6 = 0;
			int splitEvery_tFileOutputDelimited_6 = 1000;
			int splitedFileNo_tFileOutputDelimited_6 = 0;
			int currentRow_tFileOutputDelimited_6 = 0;

			final String OUT_DELIM_tFileOutputDelimited_6 = /**
															 * Start field
															 * tFileOutputDelimited_6:FIELDSEPARATOR
															 */
			";"/** End field tFileOutputDelimited_6:FIELDSEPARATOR */
			;

			final String OUT_DELIM_ROWSEP_tFileOutputDelimited_6 = /**
																	 * Start
																	 * field
																	 * tFileOutputDelimited_6:ROWSEPARATOR
																	 */
			"\n"/** End field tFileOutputDelimited_6:ROWSEPARATOR */
			;

			// create directory only if not exists
			if (directory_tFileOutputDelimited_6 != null
					&& directory_tFileOutputDelimited_6.trim().length() != 0) {
				java.io.File dir_tFileOutputDelimited_6 = new java.io.File(
						directory_tFileOutputDelimited_6);
				if (!dir_tFileOutputDelimited_6.exists()) {
					dir_tFileOutputDelimited_6.mkdirs();
				}
			}

			java.io.BufferedWriter outtFileOutputDelimited_6 = new java.io.BufferedWriter(
					new java.io.OutputStreamWriter(
							new java.io.FileOutputStream(
									fileName_tFileOutputDelimited_6, false),
							"ISO-8859-15"));
			java.io.File filetFileOutputDelimited_6 = new java.io.File(
					fileName_tFileOutputDelimited_6);

			/**
			 * [tFileOutputDelimited_6 begin ] stop
			 */

			/**
			 * [tMapPersistent_1 begin ] start
			 */

			ok_Hash.put("tMapPersistent_1", false);
			start_Hash.put("tMapPersistent_1", System.currentTimeMillis());
			currentComponent = "tMapPersistent_1";

			// ###############################
			// # Lookup's keys initialization

			// org.talend.designer.components.commons.AdvancedMemoryLookup<row2Struct>
			// tHash_Lookup_row2 =
			// (org.talend.designer.components.commons.AdvancedMemoryLookup<row2Struct>)
			// globalMap.get( "tHash_Lookup_row2" );
			org.talend.designer.components.persistent.PersistentSortedAdvancedLookup<row2Struct> tHash_Lookup_row2 = (org.talend.designer.components.persistent.PersistentSortedAdvancedLookup<row2Struct>) globalMap
					.get("tHash_Lookup_row2");

			row2Struct row2HashKey = new row2Struct();
			row2Struct row2Default = new row2Struct();

			// org.talend.designer.components.commons.AdvancedMemoryLookup<row3Struct>
			// tHash_Lookup_row3 =
			// (org.talend.designer.components.commons.AdvancedMemoryLookup<row3Struct>)
			// globalMap.get( "tHash_Lookup_row3" );
			org.talend.designer.components.persistent.PersistentSortedAdvancedLookup<row3Struct> tHash_Lookup_row3 = (org.talend.designer.components.persistent.PersistentSortedAdvancedLookup<row3Struct>) globalMap
					.get("tHash_Lookup_row3");

			row3Struct row3HashKey = new row3Struct();
			row3Struct row3Default = new row3Struct();
			// ###############################

			// ###############################
			// # Vars initialization
			// ###############################

			// ###############################
			// # Outputs initialization
			OUTStruct OUT_tmp = new OUTStruct();
			REJECTStruct REJECT_tmp = new REJECTStruct();
			REJECT_WITH_FILTERStruct REJECT_WITH_FILTER_tmp = new REJECT_WITH_FILTERStruct();
			REJECT_INNER_JOINStruct REJECT_INNER_JOIN_tmp = new REJECT_INNER_JOINStruct();
			REJECT_INNER_JOIN_WITH_FILTERStruct REJECT_INNER_JOIN_WITH_FILTER_tmp = new REJECT_INNER_JOIN_WITH_FILTERStruct();
			// ###############################

			class SortableRow_row1 implements Comparable<SortableRow_row1>,
					routines.system.IPersistableRow { // G_TM_B_001

				boolean is__rejectedInnerJoin;

				Integer exprKey_row2__ID_LOOKUP1;
				Integer row1__ID_MAIN;
				String row1__LABEL_MAIN;

				public void fillFrom(row1Struct row1,
						Integer exprKey_row2__ID_LOOKUP1) {

					this.row1__ID_MAIN = row1.ID_MAIN;
					this.row1__LABEL_MAIN = row1.LABEL_MAIN;
					this.exprKey_row2__ID_LOOKUP1 = exprKey_row2__ID_LOOKUP1;

				}

				public void copyDataTo(row1Struct row1) {

					row1.ID_MAIN = this.row1__ID_MAIN;
					row1.LABEL_MAIN = this.row1__LABEL_MAIN;

				}

				public String toString() {

					StringBuilder sb = new StringBuilder();
					sb.append(super.toString());
					sb.append("[");

					sb.append("row1__ID_MAIN");
					sb.append("=");
					sb.append(String.valueOf(this.row1__ID_MAIN));

					sb.append(", ");

					sb.append("row1__LABEL_MAIN");
					sb.append("=");
					sb.append(String.valueOf(this.row1__LABEL_MAIN));

					sb.append("]");

					return sb.toString();
				}

				public int compareTo(SortableRow_row1 other) {

					int returnValue = 0;

					returnValue = checkNullsAndCompare(
							this.exprKey_row2__ID_LOOKUP1,
							other.exprKey_row2__ID_LOOKUP1);
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
							this.row1__ID_MAIN = null;
						} else {
							this.row1__ID_MAIN = dis.readInt();
						}

						length = dis.readInt();
						if (length == -1) {
							this.row1__LABEL_MAIN = null;
						} else {
							byteArray = new byte[length];
							dis.read(byteArray);
							this.row1__LABEL_MAIN = new String(byteArray);
						}

						length = dis.readByte();
						if (length == -1) {
							this.exprKey_row2__ID_LOOKUP1 = null;
						} else {
							this.exprKey_row2__ID_LOOKUP1 = dis.readInt();
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

						if (this.row1__ID_MAIN == null) {
							dos.writeByte(-1);
						} else {
							dos.writeByte(0);
							dos.writeInt(this.row1__ID_MAIN);
						}

						if (this.row1__LABEL_MAIN == null) {
							dos.writeInt(-1);
						} else {
							byteArray = this.row1__LABEL_MAIN.getBytes();
							dos.writeInt(byteArray.length);
							dos.write(byteArray);
						}

						if (this.exprKey_row2__ID_LOOKUP1 == null) {
							dos.writeByte(-1);
						} else {
							dos.writeByte(0);
							dos.writeInt(this.exprKey_row2__ID_LOOKUP1);
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

			org.talend.designer.components.persistent.PersistentRowSorterIterator<SortableRow_row1> fsi_row1 = new org.talend.designer.components.persistent.PersistentRowSorterIterator<SortableRow_row1>(
					"/home/amaumont/data/dev/projets/Talend/hashfile/sort/data_row1") {
				public SortableRow_row1 createRowInstance() {
					return new SortableRow_row1();
				}
			};

			fsi_row1.initPut();

			class SortableRow_row1__row2 implements
					Comparable<SortableRow_row1__row2>,
					routines.system.IPersistableRow { // G_TM_B_001

				boolean is__rejectedInnerJoin;

				Integer exprKey_row3__ID_LOOKUP2;
				Integer row1__ID_MAIN;
				String row1__LABEL_MAIN;
				Integer row2__ID_LOOKUP1;
				String row2__LABEL_LOOKUP1;

				public void fillFrom(row1Struct row1, row2Struct row2,
						Integer exprKey_row3__ID_LOOKUP2) {

					this.row1__ID_MAIN = row1.ID_MAIN;
					this.row1__LABEL_MAIN = row1.LABEL_MAIN;
					this.row2__ID_LOOKUP1 = row2.ID_LOOKUP1;
					this.row2__LABEL_LOOKUP1 = row2.LABEL_LOOKUP1;
					this.exprKey_row3__ID_LOOKUP2 = exprKey_row3__ID_LOOKUP2;

				}

				public void copyDataTo(row1Struct row1, row2Struct row2) {

					row1.ID_MAIN = this.row1__ID_MAIN;
					row1.LABEL_MAIN = this.row1__LABEL_MAIN;
					row2.ID_LOOKUP1 = this.row2__ID_LOOKUP1;
					row2.LABEL_LOOKUP1 = this.row2__LABEL_LOOKUP1;

				}

				public String toString() {

					StringBuilder sb = new StringBuilder();
					sb.append(super.toString());
					sb.append("[");

					sb.append("row1__ID_MAIN");
					sb.append("=");
					sb.append(String.valueOf(this.row1__ID_MAIN));

					sb.append(", ");

					sb.append("row1__LABEL_MAIN");
					sb.append("=");
					sb.append(String.valueOf(this.row1__LABEL_MAIN));

					sb.append(", ");

					sb.append("row2__ID_LOOKUP1");
					sb.append("=");
					sb.append(String.valueOf(this.row2__ID_LOOKUP1));

					sb.append(", ");

					sb.append("row2__LABEL_LOOKUP1");
					sb.append("=");
					sb.append(String.valueOf(this.row2__LABEL_LOOKUP1));

					sb.append("]");

					return sb.toString();
				}

				public int compareTo(SortableRow_row1__row2 other) {

					int returnValue = 0;

					returnValue = checkNullsAndCompare(
							this.exprKey_row3__ID_LOOKUP2,
							other.exprKey_row3__ID_LOOKUP2);
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
							this.row1__ID_MAIN = null;
						} else {
							this.row1__ID_MAIN = dis.readInt();
						}

						length = dis.readInt();
						if (length == -1) {
							this.row1__LABEL_MAIN = null;
						} else {
							byteArray = new byte[length];
							dis.read(byteArray);
							this.row1__LABEL_MAIN = new String(byteArray);
						}

						length = dis.readByte();
						if (length == -1) {
							this.row2__ID_LOOKUP1 = null;
						} else {
							this.row2__ID_LOOKUP1 = dis.readInt();
						}

						length = dis.readInt();
						if (length == -1) {
							this.row2__LABEL_LOOKUP1 = null;
						} else {
							byteArray = new byte[length];
							dis.read(byteArray);
							this.row2__LABEL_LOOKUP1 = new String(byteArray);
						}

						length = dis.readByte();
						if (length == -1) {
							this.exprKey_row3__ID_LOOKUP2 = null;
						} else {
							this.exprKey_row3__ID_LOOKUP2 = dis.readInt();
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

						if (this.row1__ID_MAIN == null) {
							dos.writeByte(-1);
						} else {
							dos.writeByte(0);
							dos.writeInt(this.row1__ID_MAIN);
						}

						if (this.row1__LABEL_MAIN == null) {
							dos.writeInt(-1);
						} else {
							byteArray = this.row1__LABEL_MAIN.getBytes();
							dos.writeInt(byteArray.length);
							dos.write(byteArray);
						}

						if (this.row2__ID_LOOKUP1 == null) {
							dos.writeByte(-1);
						} else {
							dos.writeByte(0);
							dos.writeInt(this.row2__ID_LOOKUP1);
						}

						if (this.row2__LABEL_LOOKUP1 == null) {
							dos.writeInt(-1);
						} else {
							byteArray = this.row2__LABEL_LOOKUP1.getBytes();
							dos.writeInt(byteArray.length);
							dos.write(byteArray);
						}

						if (this.exprKey_row3__ID_LOOKUP2 == null) {
							dos.writeByte(-1);
						} else {
							dos.writeByte(0);
							dos.writeInt(this.exprKey_row3__ID_LOOKUP2);
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

			org.talend.designer.components.persistent.PersistentRowSorterIterator<SortableRow_row1__row2> fsi_row1__row2 = new org.talend.designer.components.persistent.PersistentRowSorterIterator<SortableRow_row1__row2>(
					"/home/amaumont/data/dev/projets/Talend/hashfile/sort/data_row1__row2") {
				public SortableRow_row1__row2 createRowInstance() {
					return new SortableRow_row1__row2();
				}
			};

			fsi_row1__row2.initPut();

			/**
			 * [tMapPersistent_1 begin ] stop
			 */

			/**
			 * [tFileInputDelimited_2 begin ] start
			 */

			ok_Hash.put("tFileInputDelimited_2", false);
			start_Hash.put("tFileInputDelimited_2", System.currentTimeMillis());
			currentComponent = "tFileInputDelimited_2";

			org.talend.fileprocess.FileInputDelimited fid_tFileInputDelimited_2 = new org.talend.fileprocess.FileInputDelimited(
					(java.lang.String) ((String) context.getProperty("rep_in"))
							+ "/main.csv", "ISO-8859-15", ";", "\n", true, 0,
					0, -1, -1);
			while (fid_tFileInputDelimited_2.nextRecord()) {
				row1 = null;
				boolean whetherReject_tFileInputDelimited_2 = false;
				row1 = new row1Struct();
				try {
					String temp_tFileInputDelimited_2 = "";
					temp_tFileInputDelimited_2 = fid_tFileInputDelimited_2
							.get(0);
					if (temp_tFileInputDelimited_2.length() > 0) {
						row1.ID_MAIN = ParserUtils
								.parseTo_Integer(temp_tFileInputDelimited_2);
					} else {
						row1.ID_MAIN = null;
					}
					row1.LABEL_MAIN = fid_tFileInputDelimited_2.get(1);

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
				// Start of branch "row1"
				if (row1 != null) {

					/**
					 * [tMapPersistent_1 main ] start
					 */

					currentComponent = "tMapPersistent_1";

					Integer exprKey_row2__ID_LOOKUP1 = row1.ID_MAIN;

					SortableRow_row1 rsc_row1 = new SortableRow_row1();

					rsc_row1.fillFrom(row1, exprKey_row2__ID_LOOKUP1);

					fsi_row1.put(rsc_row1);

				}// End of branch temporary "row1"

			} // end temporary main loop

			fsi_row1.endPut();

			// ###############################
			// # Input tables (lookups)
			boolean rejectedInnerJoin_tMapPersistent_1 = false;

			tHash_Lookup_row2.initGet();

			SortableRow_row1 rsc_row1;

			while (fsi_row1.hasNext()) { // G_TM_M_250 loop "row1"

				rsc_row1 = (SortableRow_row1) fsi_row1.next();
				rsc_row1.copyDataTo(row1);

				rejectedInnerJoin_tMapPersistent_1 = rsc_row1.is__rejectedInnerJoin;

				boolean forceLooprow2 = false;

				row2Struct row2ObjectFromLookup = null;

				if (!rejectedInnerJoin_tMapPersistent_1) { // G 20

					row2HashKey.ID_LOOKUP1 = rsc_row1.exprKey_row2__ID_LOOKUP1;

					tHash_Lookup_row2.lookup(row2HashKey);

					if (!tHash_Lookup_row2.hasNext()) { // G 090

						rejectedInnerJoin_tMapPersistent_1 = true;

						forceLooprow2 = true;

					} // G 090

				} // G 20

				else { // G 20 - G 21
					forceLooprow2 = true;
				} // G 21

				row2Struct row2 = null;

				while (tHash_Lookup_row2.hasNext() || forceLooprow2) { // G_TM_M_043

					row2Struct fromLookup_row2 = null;
					row2 = row2Default;

					if (!forceLooprow2) { // G 46

						fromLookup_row2 = tHash_Lookup_row2.next();

						if (fromLookup_row2 != null) {
							row2 = fromLookup_row2;
						}

						if (!rejectedInnerJoin_tMapPersistent_1 &&

						!(

						row1.ID_MAIN > 1

						)

						) { // G 016

							fromLookup_row2 = null;
							row2 = row2Default;

							rejectedInnerJoin_tMapPersistent_1 = true;

						} // G 016

					} // G 46

					forceLooprow2 = false;

					Integer exprKey_row3__ID_LOOKUP2 = 0;

					if (!rejectedInnerJoin_tMapPersistent_1) { // G_TM_M_255

						exprKey_row3__ID_LOOKUP2 = row2.ID_LOOKUP1;

					} // G_TM_M_255

					SortableRow_row1__row2 rsc_row1__row2 = new SortableRow_row1__row2();

					rsc_row1__row2.fillFrom(row1, row2,
							exprKey_row3__ID_LOOKUP2);

					rsc_row1__row2.is__rejectedInnerJoin = rejectedInnerJoin_tMapPersistent_1;

					rejectedInnerJoin_tMapPersistent_1 = false;

					// Sort and store in KeyFile(s) and DataFile(s)
					fsi_row1__row2.put(rsc_row1__row2);

					if (forceLooprow2) {
						break;
					}

				} // G_TM_M_043 loop row2

			} // G_TM_M_250 loop "row1"

			fsi_row1__row2.endPut();

			tHash_Lookup_row3.initGet();

			SortableRow_row1__row2 rsc_row1__row2;

			row2Struct row2 = new row2Struct();

			while (fsi_row1__row2.hasNext()) { // G_TM_M_250 loop "row1__row2"

				rsc_row1__row2 = (SortableRow_row1__row2) fsi_row1__row2.next();
				rsc_row1__row2.copyDataTo(row1, row2);

				rejectedInnerJoin_tMapPersistent_1 = rsc_row1__row2.is__rejectedInnerJoin;

				boolean forceLooprow3 = false;

				row3Struct row3ObjectFromLookup = null;

				if (!rejectedInnerJoin_tMapPersistent_1) { // G 20

					row3HashKey.ID_LOOKUP2 = rsc_row1__row2.exprKey_row3__ID_LOOKUP2;

					tHash_Lookup_row3.lookup(row3HashKey);

					if (!tHash_Lookup_row3.hasNext()) { // G 090

					} // G 090

				} // G 20

				row3Struct row3 = null;

				row3Struct fromLookup_row3 = null;
				row3 = row3Default;

				if (tHash_Lookup_row3.hasNext()) { // G 099

					fromLookup_row3 = tHash_Lookup_row3.next();

				} // G 099

				if (fromLookup_row3 != null) {
					row3 = fromLookup_row3;
				}

				{ // Dummy bracket to open block for "row1"

					System.out.println(row1.toString());
					System.out.println(row2.toString());
					System.out.println(row3.toString());
					System.out.println();

					// ###############################
					{ // start of Var scope

						// ###############################
						// # Vars tables
						// ###############################
						// ###############################
						// # Output tables

						OUT = null;
						REJECT_INNER_JOIN = null;
						REJECT_INNER_JOIN_WITH_FILTER = null;
						REJECT = null;
						REJECT_WITH_FILTER = null;

						boolean rejected_tMapPersistent_1 = true;
						if (!rejectedInnerJoin_tMapPersistent_1) {

							// # Output table : 'OUT'
							// # Filter conditions
							if (

							row1.ID_MAIN > 4

							) {
								rejected_tMapPersistent_1 = false;
								OUT_tmp.ID_MAIN = row1.ID_MAIN;
								OUT_tmp.LABEL_MAIN = row1.LABEL_MAIN;
								OUT_tmp.ID_LOOKUP1 = row2.ID_LOOKUP1;
								OUT_tmp.LABEL_LOOKUP1 = row2.LABEL_LOOKUP1;
								OUT_tmp.ID_LOOKUP2 = row3.ID_LOOKUP2;
								OUT_tmp.LABEL_LOOKUP2 = row3.LABEL_LOOKUP2;
								OUT = OUT_tmp;
							} // closing filter/reject
						} // closing inner join bracket (1)
						else {
							rejected_tMapPersistent_1 = false;
						} // closing else inner join bracket (1)
						// ###### START REJECTS #####

						// # Output reject table : 'REJECT_INNER_JOIN'
						// # Filter conditions
						if (rejectedInnerJoin_tMapPersistent_1) {
							REJECT_INNER_JOIN_tmp.newColumn = "REJECT INNER JOIN";
							REJECT_INNER_JOIN_tmp.ID_MAIN = row1.ID_MAIN;
							REJECT_INNER_JOIN_tmp.LABEL_MAIN = row1.LABEL_MAIN;
							REJECT_INNER_JOIN_tmp.ID_LOOKUP1 = row2.ID_LOOKUP1;
							REJECT_INNER_JOIN_tmp.LABEL_LOOKUP1 = row2.LABEL_LOOKUP1;
							REJECT_INNER_JOIN_tmp.ID_LOOKUP2 = row3.ID_LOOKUP2;
							REJECT_INNER_JOIN_tmp.LABEL_LOOKUP2 = row3.LABEL_LOOKUP2;
							REJECT_INNER_JOIN = REJECT_INNER_JOIN_tmp;
						} // closing filter/reject

						// # Output reject table :
						// 'REJECT_INNER_JOIN_WITH_FILTER'
						// # Filter conditions
						if (rejectedInnerJoin_tMapPersistent_1 && (

						row1.ID_MAIN == 1 || row1.ID_MAIN > 5

						)) {
							REJECT_INNER_JOIN_WITH_FILTER_tmp.newColumn = "REJECT INNER JOIN";
							REJECT_INNER_JOIN_WITH_FILTER_tmp.ID_MAIN = row1.ID_MAIN;
							REJECT_INNER_JOIN_WITH_FILTER_tmp.LABEL_MAIN = row1.LABEL_MAIN;
							REJECT_INNER_JOIN_WITH_FILTER_tmp.ID_LOOKUP1 = row2.ID_LOOKUP1;
							REJECT_INNER_JOIN_WITH_FILTER_tmp.LABEL_LOOKUP1 = row2.LABEL_LOOKUP1;
							REJECT_INNER_JOIN_WITH_FILTER_tmp.ID_LOOKUP2 = row3.ID_LOOKUP2;
							REJECT_INNER_JOIN_WITH_FILTER_tmp.LABEL_LOOKUP2 = row3.LABEL_LOOKUP2;
							REJECT_INNER_JOIN_WITH_FILTER = REJECT_INNER_JOIN_WITH_FILTER_tmp;
						} // closing filter/reject

						// # Output reject table : 'REJECT'
						// # Filter conditions
						if (rejected_tMapPersistent_1) {
							REJECT_tmp.newColumn = "REJECT";
							REJECT_tmp.ID_MAIN = row1.ID_MAIN;
							REJECT_tmp.LABEL_MAIN = row1.LABEL_MAIN;
							REJECT_tmp.ID_LOOKUP1 = row2.ID_LOOKUP1;
							REJECT_tmp.LABEL_LOOKUP1 = row2.LABEL_LOOKUP1;
							REJECT_tmp.ID_LOOKUP2 = row3.ID_LOOKUP2;
							REJECT_tmp.LABEL_LOOKUP2 = row3.LABEL_LOOKUP2;
							REJECT = REJECT_tmp;
						} // closing filter/reject

						// # Output reject table : 'REJECT_WITH_FILTER'
						// # Filter conditions
						if (rejected_tMapPersistent_1 && (

						row1.ID_MAIN > 3

						)) {
							REJECT_WITH_FILTER_tmp.newColumn = "REJECT WITH_FILTER";
							REJECT_WITH_FILTER_tmp.ID_MAIN = row1.ID_MAIN;
							REJECT_WITH_FILTER_tmp.LABEL_MAIN = row1.LABEL_MAIN;
							REJECT_WITH_FILTER_tmp.ID_LOOKUP1 = row2.ID_LOOKUP1;
							REJECT_WITH_FILTER_tmp.LABEL_LOOKUP1 = row2.LABEL_LOOKUP1;
							REJECT_WITH_FILTER_tmp.ID_LOOKUP2 = row3.ID_LOOKUP2;
							REJECT_WITH_FILTER_tmp.LABEL_LOOKUP2 = row3.LABEL_LOOKUP2;
							REJECT_WITH_FILTER = REJECT_WITH_FILTER_tmp;
						} // closing filter/reject
						// ###############################

					} // end of Var scope

					rejectedInnerJoin_tMapPersistent_1 = false;

					/**
					 * [tMapPersistent_1 main ] stop
					 */
					// Start of branch "OUT"
					if (OUT != null) {

						/**
						 * [tFileOutputDelimited_3 main ] start
						 */

						currentComponent = "tFileOutputDelimited_3";

						StringBuilder sb_tFileOutputDelimited_3 = new StringBuilder();

						if (OUT.ID_MAIN != null) {

							sb_tFileOutputDelimited_3.append(

							OUT.ID_MAIN

							);

						}

						sb_tFileOutputDelimited_3
								.append(OUT_DELIM_tFileOutputDelimited_3);

						if (OUT.LABEL_MAIN != null) {

							sb_tFileOutputDelimited_3.append(

							OUT.LABEL_MAIN

							);

						}

						sb_tFileOutputDelimited_3
								.append(OUT_DELIM_tFileOutputDelimited_3);

						if (OUT.ID_LOOKUP1 != null) {

							sb_tFileOutputDelimited_3.append(

							OUT.ID_LOOKUP1

							);

						}

						sb_tFileOutputDelimited_3
								.append(OUT_DELIM_tFileOutputDelimited_3);

						if (OUT.LABEL_LOOKUP1 != null) {

							sb_tFileOutputDelimited_3.append(

							OUT.LABEL_LOOKUP1

							);

						}

						sb_tFileOutputDelimited_3
								.append(OUT_DELIM_tFileOutputDelimited_3);

						if (OUT.ID_LOOKUP2 != null) {

							sb_tFileOutputDelimited_3.append(

							OUT.ID_LOOKUP2

							);

						}

						sb_tFileOutputDelimited_3
								.append(OUT_DELIM_tFileOutputDelimited_3);

						if (OUT.LABEL_LOOKUP2 != null) {

							sb_tFileOutputDelimited_3.append(

							OUT.LABEL_LOOKUP2

							);

						}

						sb_tFileOutputDelimited_3
								.append(OUT_DELIM_ROWSEP_tFileOutputDelimited_3);

						outtFileOutputDelimited_3
								.write(sb_tFileOutputDelimited_3.toString());

						nb_line_tFileOutputDelimited_3++;

						/**
						 * [tFileOutputDelimited_3 main ] stop
						 */

					} // End of branch "OUT"

					// Start of branch "REJECT"
					if (REJECT != null) {

						/**
						 * [tFileOutputDelimited_2 main ] start
						 */

						currentComponent = "tFileOutputDelimited_2";

						StringBuilder sb_tFileOutputDelimited_2 = new StringBuilder();

						if (REJECT.newColumn != null) {

							sb_tFileOutputDelimited_2.append(

							REJECT.newColumn

							);

						}

						sb_tFileOutputDelimited_2
								.append(OUT_DELIM_tFileOutputDelimited_2);

						if (REJECT.ID_MAIN != null) {

							sb_tFileOutputDelimited_2.append(

							REJECT.ID_MAIN

							);

						}

						sb_tFileOutputDelimited_2
								.append(OUT_DELIM_tFileOutputDelimited_2);

						if (REJECT.LABEL_MAIN != null) {

							sb_tFileOutputDelimited_2.append(

							REJECT.LABEL_MAIN

							);

						}

						sb_tFileOutputDelimited_2
								.append(OUT_DELIM_tFileOutputDelimited_2);

						if (REJECT.ID_LOOKUP1 != null) {

							sb_tFileOutputDelimited_2.append(

							REJECT.ID_LOOKUP1

							);

						}

						sb_tFileOutputDelimited_2
								.append(OUT_DELIM_tFileOutputDelimited_2);

						if (REJECT.LABEL_LOOKUP1 != null) {

							sb_tFileOutputDelimited_2.append(

							REJECT.LABEL_LOOKUP1

							);

						}

						sb_tFileOutputDelimited_2
								.append(OUT_DELIM_tFileOutputDelimited_2);

						if (REJECT.ID_LOOKUP2 != null) {

							sb_tFileOutputDelimited_2.append(

							REJECT.ID_LOOKUP2

							);

						}

						sb_tFileOutputDelimited_2
								.append(OUT_DELIM_tFileOutputDelimited_2);

						if (REJECT.LABEL_LOOKUP2 != null) {

							sb_tFileOutputDelimited_2.append(

							REJECT.LABEL_LOOKUP2

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

					} // End of branch "REJECT"

					// Start of branch "REJECT_WITH_FILTER"
					if (REJECT_WITH_FILTER != null) {

						/**
						 * [tFileOutputDelimited_5 main ] start
						 */

						currentComponent = "tFileOutputDelimited_5";

						StringBuilder sb_tFileOutputDelimited_5 = new StringBuilder();

						if (REJECT_WITH_FILTER.newColumn != null) {

							sb_tFileOutputDelimited_5.append(

							REJECT_WITH_FILTER.newColumn

							);

						}

						sb_tFileOutputDelimited_5
								.append(OUT_DELIM_tFileOutputDelimited_5);

						if (REJECT_WITH_FILTER.ID_MAIN != null) {

							sb_tFileOutputDelimited_5.append(

							REJECT_WITH_FILTER.ID_MAIN

							);

						}

						sb_tFileOutputDelimited_5
								.append(OUT_DELIM_tFileOutputDelimited_5);

						if (REJECT_WITH_FILTER.LABEL_MAIN != null) {

							sb_tFileOutputDelimited_5.append(

							REJECT_WITH_FILTER.LABEL_MAIN

							);

						}

						sb_tFileOutputDelimited_5
								.append(OUT_DELIM_tFileOutputDelimited_5);

						if (REJECT_WITH_FILTER.ID_LOOKUP1 != null) {

							sb_tFileOutputDelimited_5.append(

							REJECT_WITH_FILTER.ID_LOOKUP1

							);

						}

						sb_tFileOutputDelimited_5
								.append(OUT_DELIM_tFileOutputDelimited_5);

						if (REJECT_WITH_FILTER.LABEL_LOOKUP1 != null) {

							sb_tFileOutputDelimited_5.append(

							REJECT_WITH_FILTER.LABEL_LOOKUP1

							);

						}

						sb_tFileOutputDelimited_5
								.append(OUT_DELIM_tFileOutputDelimited_5);

						if (REJECT_WITH_FILTER.ID_LOOKUP2 != null) {

							sb_tFileOutputDelimited_5.append(

							REJECT_WITH_FILTER.ID_LOOKUP2

							);

						}

						sb_tFileOutputDelimited_5
								.append(OUT_DELIM_tFileOutputDelimited_5);

						if (REJECT_WITH_FILTER.LABEL_LOOKUP2 != null) {

							sb_tFileOutputDelimited_5.append(

							REJECT_WITH_FILTER.LABEL_LOOKUP2

							);

						}

						sb_tFileOutputDelimited_5
								.append(OUT_DELIM_ROWSEP_tFileOutputDelimited_5);

						outtFileOutputDelimited_5
								.write(sb_tFileOutputDelimited_5.toString());

						nb_line_tFileOutputDelimited_5++;

						/**
						 * [tFileOutputDelimited_5 main ] stop
						 */

					} // End of branch "REJECT_WITH_FILTER"

					// Start of branch "REJECT_INNER_JOIN"
					if (REJECT_INNER_JOIN != null) {

						/**
						 * [tFileOutputDelimited_1 main ] start
						 */

						currentComponent = "tFileOutputDelimited_1";

						StringBuilder sb_tFileOutputDelimited_1 = new StringBuilder();

						if (REJECT_INNER_JOIN.newColumn != null) {

							sb_tFileOutputDelimited_1.append(

							REJECT_INNER_JOIN.newColumn

							);

						}

						sb_tFileOutputDelimited_1
								.append(OUT_DELIM_tFileOutputDelimited_1);

						if (REJECT_INNER_JOIN.ID_MAIN != null) {

							sb_tFileOutputDelimited_1.append(

							REJECT_INNER_JOIN.ID_MAIN

							);

						}

						sb_tFileOutputDelimited_1
								.append(OUT_DELIM_tFileOutputDelimited_1);

						if (REJECT_INNER_JOIN.LABEL_MAIN != null) {

							sb_tFileOutputDelimited_1.append(

							REJECT_INNER_JOIN.LABEL_MAIN

							);

						}

						sb_tFileOutputDelimited_1
								.append(OUT_DELIM_tFileOutputDelimited_1);

						if (REJECT_INNER_JOIN.ID_LOOKUP1 != null) {

							sb_tFileOutputDelimited_1.append(

							REJECT_INNER_JOIN.ID_LOOKUP1

							);

						}

						sb_tFileOutputDelimited_1
								.append(OUT_DELIM_tFileOutputDelimited_1);

						if (REJECT_INNER_JOIN.LABEL_LOOKUP1 != null) {

							sb_tFileOutputDelimited_1.append(

							REJECT_INNER_JOIN.LABEL_LOOKUP1

							);

						}

						sb_tFileOutputDelimited_1
								.append(OUT_DELIM_tFileOutputDelimited_1);

						if (REJECT_INNER_JOIN.ID_LOOKUP2 != null) {

							sb_tFileOutputDelimited_1.append(

							REJECT_INNER_JOIN.ID_LOOKUP2

							);

						}

						sb_tFileOutputDelimited_1
								.append(OUT_DELIM_tFileOutputDelimited_1);

						if (REJECT_INNER_JOIN.LABEL_LOOKUP2 != null) {

							sb_tFileOutputDelimited_1.append(

							REJECT_INNER_JOIN.LABEL_LOOKUP2

							);

						}

						sb_tFileOutputDelimited_1
								.append(OUT_DELIM_ROWSEP_tFileOutputDelimited_1);

						outtFileOutputDelimited_1
								.write(sb_tFileOutputDelimited_1.toString());

						nb_line_tFileOutputDelimited_1++;

						/**
						 * [tFileOutputDelimited_1 main ] stop
						 */

					} // End of branch "REJECT_INNER_JOIN"

					// Start of branch "REJECT_INNER_JOIN_WITH_FILTER"
					if (REJECT_INNER_JOIN_WITH_FILTER != null) {

						/**
						 * [tFileOutputDelimited_6 main ] start
						 */

						currentComponent = "tFileOutputDelimited_6";

						StringBuilder sb_tFileOutputDelimited_6 = new StringBuilder();

						if (REJECT_INNER_JOIN_WITH_FILTER.newColumn != null) {

							sb_tFileOutputDelimited_6.append(

							REJECT_INNER_JOIN_WITH_FILTER.newColumn

							);

						}

						sb_tFileOutputDelimited_6
								.append(OUT_DELIM_tFileOutputDelimited_6);

						if (REJECT_INNER_JOIN_WITH_FILTER.ID_MAIN != null) {

							sb_tFileOutputDelimited_6.append(

							REJECT_INNER_JOIN_WITH_FILTER.ID_MAIN

							);

						}

						sb_tFileOutputDelimited_6
								.append(OUT_DELIM_tFileOutputDelimited_6);

						if (REJECT_INNER_JOIN_WITH_FILTER.LABEL_MAIN != null) {

							sb_tFileOutputDelimited_6.append(

							REJECT_INNER_JOIN_WITH_FILTER.LABEL_MAIN

							);

						}

						sb_tFileOutputDelimited_6
								.append(OUT_DELIM_tFileOutputDelimited_6);

						if (REJECT_INNER_JOIN_WITH_FILTER.ID_LOOKUP1 != null) {

							sb_tFileOutputDelimited_6.append(

							REJECT_INNER_JOIN_WITH_FILTER.ID_LOOKUP1

							);

						}

						sb_tFileOutputDelimited_6
								.append(OUT_DELIM_tFileOutputDelimited_6);

						if (REJECT_INNER_JOIN_WITH_FILTER.LABEL_LOOKUP1 != null) {

							sb_tFileOutputDelimited_6.append(

							REJECT_INNER_JOIN_WITH_FILTER.LABEL_LOOKUP1

							);

						}

						sb_tFileOutputDelimited_6
								.append(OUT_DELIM_tFileOutputDelimited_6);

						if (REJECT_INNER_JOIN_WITH_FILTER.ID_LOOKUP2 != null) {

							sb_tFileOutputDelimited_6.append(

							REJECT_INNER_JOIN_WITH_FILTER.ID_LOOKUP2

							);

						}

						sb_tFileOutputDelimited_6
								.append(OUT_DELIM_tFileOutputDelimited_6);

						if (REJECT_INNER_JOIN_WITH_FILTER.LABEL_LOOKUP2 != null) {

							sb_tFileOutputDelimited_6.append(

							REJECT_INNER_JOIN_WITH_FILTER.LABEL_LOOKUP2

							);

						}

						sb_tFileOutputDelimited_6
								.append(OUT_DELIM_ROWSEP_tFileOutputDelimited_6);

						outtFileOutputDelimited_6
								.write(sb_tFileOutputDelimited_6.toString());

						nb_line_tFileOutputDelimited_6++;

						/**
						 * [tFileOutputDelimited_6 main ] stop
						 */

					} // End of branch "REJECT_INNER_JOIN_WITH_FILTER"

				} // End of branch "row1"

				/**
				 * [tFileInputDelimited_2 end ] start
				 */

				currentComponent = "tFileInputDelimited_2";

			}
			fid_tFileInputDelimited_2.close();
			globalMap.put("tFileInputDelimited_2_NB_LINE",
					fid_tFileInputDelimited_2.getRowNumber());

			ok_Hash.put("tFileInputDelimited_2", true);
			end_Hash.put("tFileInputDelimited_2", System.currentTimeMillis());

			/**
			 * [tFileInputDelimited_2 end ] stop
			 */
			/**
			 * [tMapPersistent_1 end ] start
			 */

			currentComponent = "tMapPersistent_1";

			// ###############################
			// # Lookup hashes releasing
			globalMap.remove("tHash_row2");
			// ###############################

			globalMap.remove("tHash_row3");
			// ###############################

			ok_Hash.put("tMapPersistent_1", true);
			end_Hash.put("tMapPersistent_1", System.currentTimeMillis());

			/**
			 * [tMapPersistent_1 end ] stop
			 */
			/**
			 * [tFileOutputDelimited_3 end ] start
			 */

			currentComponent = "tFileOutputDelimited_3";

			outtFileOutputDelimited_3.close();
			globalMap.put("tFileOutputDelimited_3_NB_LINE",
					nb_line_tFileOutputDelimited_3);

			ok_Hash.put("tFileOutputDelimited_3", true);
			end_Hash.put("tFileOutputDelimited_3", System.currentTimeMillis());

			/**
			 * [tFileOutputDelimited_3 end ] stop
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

			/**
			 * [tFileOutputDelimited_5 end ] start
			 */

			currentComponent = "tFileOutputDelimited_5";

			outtFileOutputDelimited_5.close();
			globalMap.put("tFileOutputDelimited_5_NB_LINE",
					nb_line_tFileOutputDelimited_5);

			ok_Hash.put("tFileOutputDelimited_5", true);
			end_Hash.put("tFileOutputDelimited_5", System.currentTimeMillis());

			/**
			 * [tFileOutputDelimited_5 end ] stop
			 */

			/**
			 * [tFileOutputDelimited_1 end ] start
			 */

			currentComponent = "tFileOutputDelimited_1";

			outtFileOutputDelimited_1.close();
			globalMap.put("tFileOutputDelimited_1_NB_LINE",
					nb_line_tFileOutputDelimited_1);

			ok_Hash.put("tFileOutputDelimited_1", true);
			end_Hash.put("tFileOutputDelimited_1", System.currentTimeMillis());

			/**
			 * [tFileOutputDelimited_1 end ] stop
			 */

			/**
			 * [tFileOutputDelimited_6 end ] start
			 */

			currentComponent = "tFileOutputDelimited_6";

			outtFileOutputDelimited_6.close();
			globalMap.put("tFileOutputDelimited_6_NB_LINE",
					nb_line_tFileOutputDelimited_6);

			ok_Hash.put("tFileOutputDelimited_6", true);
			end_Hash.put("tFileOutputDelimited_6", System.currentTimeMillis());

			/**
			 * [tFileOutputDelimited_6 end ] stop
			 */

			tFileCompare_1Process();

		} catch (Exception e) {

			throw new TalendException(this, e);
		}

		finally {
			// free memory for "tMapPersistent_1"
			globalMap.put("tHash_Lookup_row2", null);

			// free memory for "tMapPersistent_1"
			globalMap.put("tHash_Lookup_row3", null);
			System.gc();
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
						new java.io.FileReader(
								(java.lang.String) ((String) context
										.getProperty("rep_out"))
										+ "out_main.csv"));
				java.io.BufferedReader fileRef_tFileCompare_1 = new java.io.BufferedReader(
						new java.io.FileReader(
								(java.lang.String) ((String) context
										.getProperty("rep_ref"))
										+ "out_main.csv"));
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
						+ "] Ok";

			} else {
				messagetFileCompare_1 = "[" + jobName + "." + currentComponent
						+ "] Files differ !!!";
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
			tFileCompare_2Process();

		} catch (Exception e) {

			throw new TalendException(this, e);
		}

	}

	public void tFileCompare_2Process() throws TalendException {
		try {

			/**
			 * [tFileCompare_2 begin ] start
			 */

			ok_Hash.put("tFileCompare_2", false);
			start_Hash.put("tFileCompare_2", System.currentTimeMillis());
			currentComponent = "tFileCompare_2";

			/**
			 * [tFileCompare_2 begin ] stop
			 */
			/**
			 * [tFileCompare_2 main ] start
			 */

			currentComponent = "tFileCompare_2";

			boolean result_tFileCompare_2 = true;

			if (result_tFileCompare_2) {

				java.io.BufferedReader file_tFileCompare_2 = new java.io.BufferedReader(
						new java.io.FileReader(
								(java.lang.String) ((String) context
										.getProperty("rep_out"))
										+ "out_reject.csv"));
				java.io.BufferedReader fileRef_tFileCompare_2 = new java.io.BufferedReader(
						new java.io.FileReader(
								(java.lang.String) ((String) context
										.getProperty("rep_ref"))
										+ "out_reject.csv"));
				String content_tFileCompare_2 = null, contentRef_tFileCompare_2 = null;
				while ((content_tFileCompare_2 = file_tFileCompare_2.readLine()) != null
						&& (contentRef_tFileCompare_2 = fileRef_tFileCompare_2
								.readLine()) != null) {
					if (content_tFileCompare_2
							.compareTo(contentRef_tFileCompare_2) != 0) {
						result_tFileCompare_2 = false;
						break;
					}
				}

				// Check if files has a different number of lines:
				if (content_tFileCompare_2 == null) {
					// This step is done in case of the while upper ignore
					// second part:
					contentRef_tFileCompare_2 = fileRef_tFileCompare_2
							.readLine();
				}
				if (content_tFileCompare_2 != null
						|| contentRef_tFileCompare_2 != null) {
					result_tFileCompare_2 = false;
				}

				file_tFileCompare_2.close();
				fileRef_tFileCompare_2.close();

			}

			String messagetFileCompare_2 = "";
			if (result_tFileCompare_2) {
				messagetFileCompare_2 = "[" + jobName + "." + currentComponent
						+ "] Ok";

			} else {
				messagetFileCompare_2 = "[" + jobName + "." + currentComponent
						+ "] Files differ !!!";
			}
			globalMap.put("tFileCompare_2_DIFFERENCE", result_tFileCompare_2);

			System.out.println(messagetFileCompare_2);

			/**
			 * [tFileCompare_2 main ] stop
			 */
			/**
			 * [tFileCompare_2 end ] start
			 */

			currentComponent = "tFileCompare_2";

			ok_Hash.put("tFileCompare_2", true);
			end_Hash.put("tFileCompare_2", System.currentTimeMillis());

			/**
			 * [tFileCompare_2 end ] stop
			 */
			tFileCompare_5Process();

		} catch (Exception e) {

			throw new TalendException(this, e);
		}

	}

	public void tFileCompare_5Process() throws TalendException {
		try {

			/**
			 * [tFileCompare_5 begin ] start
			 */

			ok_Hash.put("tFileCompare_5", false);
			start_Hash.put("tFileCompare_5", System.currentTimeMillis());
			currentComponent = "tFileCompare_5";

			/**
			 * [tFileCompare_5 begin ] stop
			 */
			/**
			 * [tFileCompare_5 main ] start
			 */

			currentComponent = "tFileCompare_5";

			boolean result_tFileCompare_5 = true;

			if (result_tFileCompare_5) {

				java.io.BufferedReader file_tFileCompare_5 = new java.io.BufferedReader(
						new java.io.FileReader(
								(java.lang.String) ((String) context
										.getProperty("rep_out"))
										+ "out_reject_with_filter.csv"));
				java.io.BufferedReader fileRef_tFileCompare_5 = new java.io.BufferedReader(
						new java.io.FileReader(
								(java.lang.String) ((String) context
										.getProperty("rep_ref"))
										+ "out_reject_with_filter.csv"));
				String content_tFileCompare_5 = null, contentRef_tFileCompare_5 = null;
				while ((content_tFileCompare_5 = file_tFileCompare_5.readLine()) != null
						&& (contentRef_tFileCompare_5 = fileRef_tFileCompare_5
								.readLine()) != null) {
					if (content_tFileCompare_5
							.compareTo(contentRef_tFileCompare_5) != 0) {
						result_tFileCompare_5 = false;
						break;
					}
				}

				// Check if files has a different number of lines:
				if (content_tFileCompare_5 == null) {
					// This step is done in case of the while upper ignore
					// second part:
					contentRef_tFileCompare_5 = fileRef_tFileCompare_5
							.readLine();
				}
				if (content_tFileCompare_5 != null
						|| contentRef_tFileCompare_5 != null) {
					result_tFileCompare_5 = false;
				}

				file_tFileCompare_5.close();
				fileRef_tFileCompare_5.close();

			}

			String messagetFileCompare_5 = "";
			if (result_tFileCompare_5) {
				messagetFileCompare_5 = "[" + jobName + "." + currentComponent
						+ "] Ok";

			} else {
				messagetFileCompare_5 = "[" + jobName + "." + currentComponent
						+ "] Files differ !!!";
			}
			globalMap.put("tFileCompare_5_DIFFERENCE", result_tFileCompare_5);

			System.out.println(messagetFileCompare_5);

			/**
			 * [tFileCompare_5 main ] stop
			 */
			/**
			 * [tFileCompare_5 end ] start
			 */

			currentComponent = "tFileCompare_5";

			ok_Hash.put("tFileCompare_5", true);
			end_Hash.put("tFileCompare_5", System.currentTimeMillis());

			/**
			 * [tFileCompare_5 end ] stop
			 */
			tFileCompare_3Process();

		} catch (Exception e) {

			throw new TalendException(this, e);
		}

	}

	public void tFileCompare_3Process() throws TalendException {
		try {

			/**
			 * [tFileCompare_3 begin ] start
			 */

			ok_Hash.put("tFileCompare_3", false);
			start_Hash.put("tFileCompare_3", System.currentTimeMillis());
			currentComponent = "tFileCompare_3";

			/**
			 * [tFileCompare_3 begin ] stop
			 */
			/**
			 * [tFileCompare_3 main ] start
			 */

			currentComponent = "tFileCompare_3";

			boolean result_tFileCompare_3 = true;

			if (result_tFileCompare_3) {

				java.io.BufferedReader file_tFileCompare_3 = new java.io.BufferedReader(
						new java.io.FileReader(
								(java.lang.String) ((String) context
										.getProperty("rep_out"))
										+ "out_reject_inner_join.csv"));
				java.io.BufferedReader fileRef_tFileCompare_3 = new java.io.BufferedReader(
						new java.io.FileReader(
								(java.lang.String) ((String) context
										.getProperty("rep_ref"))
										+ "out_reject_inner_join.csv"));
				String content_tFileCompare_3 = null, contentRef_tFileCompare_3 = null;
				while ((content_tFileCompare_3 = file_tFileCompare_3.readLine()) != null
						&& (contentRef_tFileCompare_3 = fileRef_tFileCompare_3
								.readLine()) != null) {
					if (content_tFileCompare_3
							.compareTo(contentRef_tFileCompare_3) != 0) {
						result_tFileCompare_3 = false;
						break;
					}
				}

				// Check if files has a different number of lines:
				if (content_tFileCompare_3 == null) {
					// This step is done in case of the while upper ignore
					// second part:
					contentRef_tFileCompare_3 = fileRef_tFileCompare_3
							.readLine();
				}
				if (content_tFileCompare_3 != null
						|| contentRef_tFileCompare_3 != null) {
					result_tFileCompare_3 = false;
				}

				file_tFileCompare_3.close();
				fileRef_tFileCompare_3.close();

			}

			String messagetFileCompare_3 = "";
			if (result_tFileCompare_3) {
				messagetFileCompare_3 = "[" + jobName + "." + currentComponent
						+ "] Ok";

			} else {
				messagetFileCompare_3 = "[" + jobName + "." + currentComponent
						+ "] Files differ !!!";
			}
			globalMap.put("tFileCompare_3_DIFFERENCE", result_tFileCompare_3);

			System.out.println(messagetFileCompare_3);

			/**
			 * [tFileCompare_3 main ] stop
			 */
			/**
			 * [tFileCompare_3 end ] start
			 */

			currentComponent = "tFileCompare_3";

			ok_Hash.put("tFileCompare_3", true);
			end_Hash.put("tFileCompare_3", System.currentTimeMillis());

			/**
			 * [tFileCompare_3 end ] stop
			 */
			tFileCompare_6Process();

		} catch (Exception e) {

			throw new TalendException(this, e);
		}

	}

	public void tFileCompare_6Process() throws TalendException {
		try {
			/**
			 * [tFileCompare_6 begin ] start
			 */

			ok_Hash.put("tFileCompare_6", false);
			start_Hash.put("tFileCompare_6", System.currentTimeMillis());
			currentComponent = "tFileCompare_6";

			/**
			 * [tFileCompare_6 begin ] stop
			 */
			/**
			 * [tFileCompare_6 main ] start
			 */

			currentComponent = "tFileCompare_6";

			boolean result_tFileCompare_6 = true;

			if (result_tFileCompare_6) {

				java.io.BufferedReader file_tFileCompare_6 = new java.io.BufferedReader(
						new java.io.FileReader(
								(java.lang.String) ((String) context
										.getProperty("rep_out"))
										+ "out_reject_inner_join_with_filter.csv"));
				java.io.BufferedReader fileRef_tFileCompare_6 = new java.io.BufferedReader(
						new java.io.FileReader(
								(java.lang.String) ((String) context
										.getProperty("rep_ref"))
										+ "out_reject_inner_join_with_filter.csv"));
				String content_tFileCompare_6 = null, contentRef_tFileCompare_6 = null;
				while ((content_tFileCompare_6 = file_tFileCompare_6.readLine()) != null
						&& (contentRef_tFileCompare_6 = fileRef_tFileCompare_6
								.readLine()) != null) {
					if (content_tFileCompare_6
							.compareTo(contentRef_tFileCompare_6) != 0) {
						result_tFileCompare_6 = false;
						break;
					}
				}

				// Check if files has a different number of lines:
				if (content_tFileCompare_6 == null) {
					// This step is done in case of the while upper ignore
					// second part:
					contentRef_tFileCompare_6 = fileRef_tFileCompare_6
							.readLine();
				}
				if (content_tFileCompare_6 != null
						|| contentRef_tFileCompare_6 != null) {
					result_tFileCompare_6 = false;
				}

				file_tFileCompare_6.close();
				fileRef_tFileCompare_6.close();

			}

			String messagetFileCompare_6 = "";
			if (result_tFileCompare_6) {
				messagetFileCompare_6 = "[" + jobName + "." + currentComponent
						+ "] Ok";

			} else {
				messagetFileCompare_6 = "[" + jobName + "." + currentComponent
						+ "] Files differ !!!";
			}
			globalMap.put("tFileCompare_6_DIFFERENCE", result_tFileCompare_6);

			System.out.println(messagetFileCompare_6);

			/**
			 * [tFileCompare_6 main ] stop
			 */
			/**
			 * [tFileCompare_6 end ] start
			 */

			currentComponent = "tFileCompare_6";

			ok_Hash.put("tFileCompare_6", true);
			end_Hash.put("tFileCompare_6", System.currentTimeMillis());

			/**
			 * [tFileCompare_6 end ] stop
			 */

		} catch (Exception e) {

			throw new TalendException(this, e);
		}

	}

	private class row2Struct implements
			routines.system.IPersistableLookupRow<row2Struct>,
			Comparable<row2Struct> {
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
			final row2Struct other = (row2Struct) obj;

			if (this.ID_LOOKUP1 == null) {
				if (other.ID_LOOKUP1 != null)
					return false;
			} else if (!this.ID_LOOKUP1.equals(other.ID_LOOKUP1))
				return false;

			return true;
		}

		public void copyDataTo(row2Struct other) {

			other.ID_LOOKUP1 = this.ID_LOOKUP1;
			other.LABEL_LOOKUP1 = this.LABEL_LOOKUP1;

		}

		public void copyKeysDataTo(row2Struct other) {

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
		public int compareTo(row2Struct other) {

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

	public void tFileInputDelimited_5Process() throws TalendException {
		try {

			row2Struct row2 = new row2Struct();

			/**
			 * [tAdvancedHashPersistent_row2 begin ] start
			 */

			ok_Hash.put("tAdvancedHashPersistent_row2", false);
			start_Hash.put("tAdvancedHashPersistent_row2", System
					.currentTimeMillis());
			currentComponent = "tAdvancedHashPersistent_row2";

			org.talend.designer.components.commons.AdvancedLookup.MATCHING_MODE matchingModeEnum_row2 = org.talend.designer.components.commons.AdvancedLookup.MATCHING_MODE.ALL_MATCHES;

			org.talend.designer.components.persistent.PersistentSortedAdvancedLookup<row2Struct> tHash_Lookup_row2 = new org.talend.designer.components.persistent.PersistentSortedAdvancedLookup<row2Struct>(
					matchingModeEnum_row2,
					"/home/amaumont/data/dev/projets/Talend/hashfile/testData/container/row2") {
				public row2Struct createRowInstance() {
					return new row2Struct();
				}
			};

			tHash_Lookup_row2.initPut();

			// org.talend.designer.components.commons.AdvancedMemoryLookup<row2Struct>
			// tHash_Lookup_row2 =
			// org.talend.designer.components.commons.AdvancedMemoryLookup.
			// <row2Struct>getLookup(matchingModeEnum_row2);

			globalMap.put("tHash_Lookup_row2", tHash_Lookup_row2);

			/**
			 * [tAdvancedHashPersistent_row2 begin ] stop
			 */

			/**
			 * [tFileInputDelimited_5 begin ] start
			 */

			ok_Hash.put("tFileInputDelimited_5", false);
			start_Hash.put("tFileInputDelimited_5", System.currentTimeMillis());
			currentComponent = "tFileInputDelimited_5";

			org.talend.fileprocess.FileInputDelimited fid_tFileInputDelimited_5 = new org.talend.fileprocess.FileInputDelimited(
					(java.lang.String) ((String) context.getProperty("rep_in"))
							+ "lookup_multi_rows.csv", "ISO-8859-15", ";",
					"\n", true, 0, 0, -1, -1);
			while (fid_tFileInputDelimited_5.nextRecord()) {
				row2 = null;
				row2 = null;
				boolean whetherReject_tFileInputDelimited_5 = false;
				row2 = new row2Struct();
				try {
					String temp_tFileInputDelimited_5 = "";
					temp_tFileInputDelimited_5 = fid_tFileInputDelimited_5
							.get(0);
					if (temp_tFileInputDelimited_5.length() > 0) {
						row2.ID_LOOKUP1 = ParserUtils
								.parseTo_Integer(temp_tFileInputDelimited_5);
					} else {
						row2.ID_LOOKUP1 = null;
					}
					row2.LABEL_LOOKUP1 = fid_tFileInputDelimited_5.get(1);

				} catch (Exception e) {
					whetherReject_tFileInputDelimited_5 = true;
					throw (e);
				}

				/**
				 * [tFileInputDelimited_5 begin ] stop
				 */
				/**
				 * [tFileInputDelimited_5 main ] start
				 */

				currentComponent = "tFileInputDelimited_5";

				/**
				 * [tFileInputDelimited_5 main ] stop
				 */
				// Start of branch "row2"
				if (row2 != null) {

					/**
					 * [tAdvancedHashPersistent_row2 main ] start
					 */

					currentComponent = "tAdvancedHashPersistent_row2";

					row2Struct row2_HashRow = new row2Struct();

					row2_HashRow.ID_LOOKUP1 = row2.ID_LOOKUP1;
					row2_HashRow.LABEL_LOOKUP1 = row2.LABEL_LOOKUP1;
					tHash_Lookup_row2.put(row2_HashRow);

					/**
					 * [tAdvancedHashPersistent_row2 main ] stop
					 */

				} // End of branch "row2"

				/**
				 * [tFileInputDelimited_5 end ] start
				 */

				currentComponent = "tFileInputDelimited_5";

			}
			fid_tFileInputDelimited_5.close();
			globalMap.put("tFileInputDelimited_5_NB_LINE",
					fid_tFileInputDelimited_5.getRowNumber());

			ok_Hash.put("tFileInputDelimited_5", true);
			end_Hash.put("tFileInputDelimited_5", System.currentTimeMillis());

			/**
			 * [tFileInputDelimited_5 end ] stop
			 */
			/**
			 * [tAdvancedHashPersistent_row2 end ] start
			 */

			currentComponent = "tAdvancedHashPersistent_row2";

			tHash_Lookup_row2.endPut();

			ok_Hash.put("tAdvancedHashPersistent_row2", true);
			end_Hash.put("tAdvancedHashPersistent_row2", System
					.currentTimeMillis());

			/**
			 * [tAdvancedHashPersistent_row2 end ] stop
			 */

		} catch (Exception e) {

			throw new TalendException(this, e);
		}

	}

	private class row3Struct implements
			routines.system.IPersistableLookupRow<row3Struct>,
			Comparable<row3Struct> {
		private static final int DEFAULT_HASHCODE = 1;
		private static final int PRIME = 31;
		private int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		Integer ID_LOOKUP2;

		String LABEL_LOOKUP2;

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime
						* result
						+ ((this.ID_LOOKUP2 == null) ? 0 : this.ID_LOOKUP2
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
			final row3Struct other = (row3Struct) obj;

			if (this.ID_LOOKUP2 == null) {
				if (other.ID_LOOKUP2 != null)
					return false;
			} else if (!this.ID_LOOKUP2.equals(other.ID_LOOKUP2))
				return false;

			return true;
		}

		public void copyDataTo(row3Struct other) {

			other.ID_LOOKUP2 = this.ID_LOOKUP2;
			other.LABEL_LOOKUP2 = this.LABEL_LOOKUP2;

		}

		public void copyKeysDataTo(row3Struct other) {

			other.ID_LOOKUP2 = this.ID_LOOKUP2;

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
					this.ID_LOOKUP2 = null;
				} else {
					this.ID_LOOKUP2 = dis.readInt();
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

				if (this.ID_LOOKUP2 == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeInt(this.ID_LOOKUP2);
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
					this.LABEL_LOOKUP2 = null;
				} else {
					byteArray = new byte[length];
					dis.read(byteArray);
					this.LABEL_LOOKUP2 = new String(byteArray);
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

				if (this.LABEL_LOOKUP2 == null) {
					dos.writeInt(-1);
				} else {
					byteArray = this.LABEL_LOOKUP2.getBytes();
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

			sb.append("ID_LOOKUP2");
			sb.append("=");
			sb.append(String.valueOf(this.ID_LOOKUP2));

			sb.append(", ");

			sb.append("LABEL_LOOKUP2");
			sb.append("=");
			sb.append(String.valueOf(this.LABEL_LOOKUP2));

			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row3Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.ID_LOOKUP2,
					other.ID_LOOKUP2);
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

	public void tFileInputDelimited_6Process() throws TalendException {
		try {

			row3Struct row3 = new row3Struct();

			/**
			 * [tAdvancedHashPersistent_row3 begin ] start
			 */

			ok_Hash.put("tAdvancedHashPersistent_row3", false);
			start_Hash.put("tAdvancedHashPersistent_row3", System
					.currentTimeMillis());
			currentComponent = "tAdvancedHashPersistent_row3";

			org.talend.designer.components.commons.AdvancedLookup.MATCHING_MODE matchingModeEnum_row3 = org.talend.designer.components.commons.AdvancedLookup.MATCHING_MODE.LAST_MATCH;

			org.talend.designer.components.persistent.PersistentSortedAdvancedLookup<row3Struct> tHash_Lookup_row3 = new org.talend.designer.components.persistent.PersistentSortedAdvancedLookup<row3Struct>(
					matchingModeEnum_row3,
					"/home/amaumont/data/dev/projets/Talend/hashfile/testData/container/row3") {
				public row3Struct createRowInstance() {
					return new row3Struct();
				}
			};

			tHash_Lookup_row3.initPut();

			// org.talend.designer.components.commons.AdvancedMemoryLookup<row3Struct>
			// tHash_Lookup_row3 =
			// org.talend.designer.components.commons.AdvancedMemoryLookup.
			// <row3Struct>getLookup(matchingModeEnum_row3);

			globalMap.put("tHash_Lookup_row3", tHash_Lookup_row3);

			/**
			 * [tAdvancedHashPersistent_row3 begin ] stop
			 */

			/**
			 * [tFileInputDelimited_6 begin ] start
			 */

			ok_Hash.put("tFileInputDelimited_6", false);
			start_Hash.put("tFileInputDelimited_6", System.currentTimeMillis());
			currentComponent = "tFileInputDelimited_6";

			org.talend.fileprocess.FileInputDelimited fid_tFileInputDelimited_6 = new org.talend.fileprocess.FileInputDelimited(
					(java.lang.String) ((String) context.getProperty("rep_in"))
							+ "lookup_multi_rows2.csv", "ISO-8859-15", ";",
					"\n", true, 0, 0, -1, -1);
			while (fid_tFileInputDelimited_6.nextRecord()) {
				row3 = null;
				row3 = null;
				boolean whetherReject_tFileInputDelimited_6 = false;
				row3 = new row3Struct();
				try {
					String temp_tFileInputDelimited_6 = "";
					temp_tFileInputDelimited_6 = fid_tFileInputDelimited_6
							.get(0);
					if (temp_tFileInputDelimited_6.length() > 0) {
						row3.ID_LOOKUP2 = ParserUtils
								.parseTo_Integer(temp_tFileInputDelimited_6);
					} else {
						row3.ID_LOOKUP2 = null;
					}
					row3.LABEL_LOOKUP2 = fid_tFileInputDelimited_6.get(1);

				} catch (Exception e) {
					whetherReject_tFileInputDelimited_6 = true;
					throw (e);
				}

				/**
				 * [tFileInputDelimited_6 begin ] stop
				 */
				/**
				 * [tFileInputDelimited_6 main ] start
				 */

				currentComponent = "tFileInputDelimited_6";

				/**
				 * [tFileInputDelimited_6 main ] stop
				 */
				// Start of branch "row3"
				if (row3 != null) {

					/**
					 * [tAdvancedHashPersistent_row3 main ] start
					 */

					currentComponent = "tAdvancedHashPersistent_row3";

					row3Struct row3_HashRow = new row3Struct();

					row3_HashRow.ID_LOOKUP2 = row3.ID_LOOKUP2;
					row3_HashRow.LABEL_LOOKUP2 = row3.LABEL_LOOKUP2;
					tHash_Lookup_row3.put(row3_HashRow);

					/**
					 * [tAdvancedHashPersistent_row3 main ] stop
					 */

				} // End of branch "row3"

				/**
				 * [tFileInputDelimited_6 end ] start
				 */

				currentComponent = "tFileInputDelimited_6";

			}
			fid_tFileInputDelimited_6.close();
			globalMap.put("tFileInputDelimited_6_NB_LINE",
					fid_tFileInputDelimited_6.getRowNumber());

			ok_Hash.put("tFileInputDelimited_6", true);
			end_Hash.put("tFileInputDelimited_6", System.currentTimeMillis());

			/**
			 * [tFileInputDelimited_6 end ] stop
			 */
			/**
			 * [tAdvancedHashPersistent_row3 end ] start
			 */

			currentComponent = "tAdvancedHashPersistent_row3";

			tHash_Lookup_row3.endPut();

			ok_Hash.put("tAdvancedHashPersistent_row3", true);
			end_Hash.put("tAdvancedHashPersistent_row3", System
					.currentTimeMillis());

			/**
			 * [tAdvancedHashPersistent_row3 end ] stop
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
	public static String contextStr = "Default";
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
		String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

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
			java.io.InputStream inContext = Copy_of_TUnit_tMap_JAVA_02.class
					.getClassLoader()
					.getResourceAsStream(
							"test/prototype/main/contexts/test2.properties");
			if (inContext != null) {
				defaultProps.load(inContext);
				inContext.close();
				context = new ContextProperties(defaultProps);
			}

			if (contextStr.compareTo("Default") != 0) {
				inContext = Copy_of_TUnit_tMap_JAVA_02.class.getClassLoader()
						.getResourceAsStream(
								"java_project_6/copy_of_tunit_tmap_java_02_0_1/contexts/"
										+ contextStr + ".properties");
				if (inContext != null) {
					context.load(inContext);
					inContext.close();
				}
			}

			if (!context_param.isEmpty()) {
				context.putAll(context_param);
			}

			context.rep_in = (String) context.getProperty("rep_in");

			context.rep_out = (String) context.getProperty("rep_out");

			context.rep_ref = (String) context.getProperty("rep_ref");

		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		long startUsedMemory = Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory();
		long endUsedMemory = 0;
		long end = 0;
		long start = System.currentTimeMillis();
		final Copy_of_TUnit_tMap_JAVA_02 Copy_of_TUnit_tMap_JAVA_02Class = new Copy_of_TUnit_tMap_JAVA_02();
		try {
			errorCode = null;
			Copy_of_TUnit_tMap_JAVA_02Class.tRunJob_1Process();
			status = "end";
		} catch (TalendException e_tRunJob_1) {
			status = "failure";
			e_tRunJob_1.printStackTrace();

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
							+ " bytes memory increase when running : Copy_of_TUnit_tMap_JAVA_02");
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
		contextStr = "Default";
		pid = "0";
		rootPid = null;
		fatherPid = null;
		context_param.clear();
		System.gc();
	}
}
/*******************************************************************************
 * 153896 characters generated by Talend OpenStudio on the 12 avril 2008
 * 18:49:54 CEST
 ******************************************************************************/
