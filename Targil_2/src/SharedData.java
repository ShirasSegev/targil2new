/** Description of SharedData
 * @author Guy Ziv	
 * @version 1
 */
public class SharedData 
{
	//commit 2
	/**Description of fields
	 * array - array numbers 
	 * winarray - boolean array
	 * flag - boolean variable
	 * b - final int from user
	 */
	private int [] array;
	private boolean [] winArray;
	private boolean flag;
	private final int b;
	
	/**
	 * @param array
	 * @param b
	 */
	public SharedData(int[] array, int b) {
		
		this.array = array;
		this.b = b;
	}

	/**
	 * @return winarray boolean array
	 */
	public boolean[] getWinArray() 
	{
		return winArray;
	}

	/**
	 * @param winArray set from user
	 */
	public void setWinArray(boolean [] winArray) 
	{
		this.winArray = winArray;
	}

	/**
	 * @return int array 
	 */
	public int[] getArray() 
	{
		return array;
	}

	/**
	 * @return b integer
	 */
	public int getB() 
	{
		return b;
	}

	/**
	 * @return boolean flag
	 */
	public boolean getFlag() 
	{
		return flag;
	}

	/**
	 * @param flag - set flag
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
