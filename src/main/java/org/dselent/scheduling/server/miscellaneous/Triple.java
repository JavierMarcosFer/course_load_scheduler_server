package org.dselent.scheduling.server.miscellaneous;

public class Triple<A, B, C>
{
	private A value1;
	private B value2;
	private C value3;
	
	public Triple(A v1, B v2, C v3)
	{
		value1 = v1;
		value2 = v2;
		value3 = v3;
	}
	
	public void setValue1(A newValue1)
	{
		value1 = newValue1;
	}
	
	public A getValue1()
	{
		return value1;
	}
	
	public void setValue2(B newValue2)
	{
		value2 = newValue2;
	}
	
	public B getValue2()
	{
		return value2;
	}
	
	public void setValue3(C newValue3)
	{
		value3 = newValue3;
	}
	
	public C getValue3()
	{
		return value3;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("{");
		sb.append(value1);
		sb.append(", ");
		sb.append(value2);
		sb.append(", ");
		sb.append(value3);
		sb.append("}");
		
		return sb.toString();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value1 == null) ? 0 : value1.hashCode());
		result = prime * result + ((value2 == null) ? 0 : value2.hashCode());
		result = prime * result + ((value3 == null) ? 0 : value3.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof Triple))
		{
			return false;
		}
		Triple other = (Triple) obj;
		
		if (value1 == null)
		{
			if (other.value1 != null)
			{
				return false;
			}
		}
		else if (!value1.equals(other.value1))
		{
			return false;
		}
		if (value2 == null)
		{
			if (other.value2 != null)
			{
				return false;
			}
		}
		else if (!value2.equals(other.value2))
		{
			return false;
		}
		if (value3 == null)
		{
			if (other.value3 != null)
			{
				return false;
			}
		}
		else if (!value3.equals(other.value3))
		{
			return false;
		}
		return true;
	}
	
}
