package io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * compress given data and write it to the output stream source
 * @author Tomer Brami & Yotam Levy
 */
public class MyCompressorOutputStream extends OutputStream{

	protected OutputStream out;
	protected int prevByte;
	protected int count;
	
	public MyCompressorOutputStream(OutputStream out) {
		super();
		this.out = out;
		this.count = 0;	
	}

	@Override
	public void write(int num) throws IOException {
		
		if(count==0)//if it is the first time we are writing something to data source
		{
			this.prevByte=num;
			this.count=1;
			return;
		}
	
		if(num==this.prevByte)//if we read the same byte,count it
		{
			
			count++;
			//if there are more than 255 bytes from the same type,write the byte than 255 and than starting count again
			if(count==256)
			{
				out.write(prevByte);
				out.write(255);
				count=1;
			}
		}
		else//new byte,lets write the previous ones
		{
			out.write(prevByte);
			out.write(count);
			this.prevByte=num;
			this.count=1;
		}
	}
	
	public OutputStream getOut() {
		return out;
	}

	public void setOut(OutputStream out) {
		this.out = out;
	}

	public int getPrevByte() {
		return prevByte;
	}

	public void setPrevByte(int prevByte) {
		this.prevByte = prevByte;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	/**
	 * writing byte array to data source
	 */
	@Override
	public void write(byte[] byteArr) throws IOException 
	{
		//because the write method that get only integer didn't write the last byte,
		//we have to override this method,and writing the last byte the data source
		super.write(byteArr);//first calling super's method
		if(count>0)//writing the last byte
		{
			
			out.write((byte)prevByte);
			out.write((byte)count);
		}
		
		//In case we continue writing after something else
		count=0;
		prevByte=0;
	}
}
