package com.roller.medicine.info;

import java.util.List;

public class MyFocusFansCommentsInfo extends BaseInfo{

	private List<MyFocusFansCommentsItemInfo> list;

	public List<MyFocusFansCommentsItemInfo> getList() {
		return list;
	}

	public void setList(List<MyFocusFansCommentsItemInfo> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "MyFocusFansInfo [list=" + list + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyFocusFansCommentsInfo other = (MyFocusFansCommentsInfo) obj;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		return true;
	}

	
	
}
