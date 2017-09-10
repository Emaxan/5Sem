using System;
using System.Collections.Generic;

namespace SPP_Laba4
{
	public static unsafe class ArrayHelper
	{
		public static unsafe long Sum(List<int> array)
		{
			long sum = 0;
			var i = 0;
			try
			{
				while (true)
				{
					sum += array[i++];
				}
			}
			catch(ArgumentOutOfRangeException ex)
			{
				throw new OverflowException($"Index Out of range. Sum is {sum}.", ex);
			}
		}
	}
}
