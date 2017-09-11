using System;
using System.Collections.Generic;

namespace SPP_Laba4.ArrayHelperExtension
{
	public static unsafe class ArrayHelperExtension
	{
		public static unsafe long Sum(this ArrayHelper.ArrayHelper helper, List<int> array)
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
