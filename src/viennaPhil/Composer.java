package viennaPhil;

public class Composer {
	private String name, nationality, musicType, years, note;

	public Composer(String name, String nationality, String musicType, String years,
			String note) {
		super();
		this.name = name;
		this.nationality = nationality;
		this.musicType = musicType;
		this.years = years;
		this.note = note;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Composer other = (Composer) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public String getNationality() {
		return nationality;
	}

	public String getMusicType() {
		return musicType;
	}

	public String getYears() {
		return years;
	}

	public String getNote() {
		return note;
	}
	
	
	
}
