package telran.spring.service;


public record Person(long id, String name, String city) {
	
	@Override
	public boolean equals(Object obj) {
		boolean res = false;
		if (!(obj == null || !(obj instanceof Person))) {
			if(((Person) obj).id == id()) {
				res = true;
			}
		}
		return res;
	}
	@Override
	public int hashCode() {
		return (int) id();
	}
}