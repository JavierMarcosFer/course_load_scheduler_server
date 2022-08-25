package org.dselent.scheduling.server.dto;

import javax.annotation.Generated;

public class CourseInstanceDto {
	
	private final Integer id;
	private final Integer course_id;
	private final String term;
	private final Integer sectionNo;
	
	@Generated("SparkTools")
	private CourseInstanceDto(Builder builder) {
		this.id = builder.id;
		this.course_id = builder.course_id;
		this.term = builder.term;
		this.sectionNo = builder.sectionNo;
	}
	/**
	 * Creates builder to build {@link CourseInstanceDto}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link CourseInstanceDto}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Integer id;
		private Integer course_id;
		private String term;
		private Integer sectionNo;

		private Builder() {
		}

		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

		public Builder withCourse_id(Integer course_id) {
			this.course_id = course_id;
			return this;
		}

		public Builder withTerm(String term) {
			this.term = term;
			return this;
		}

		public Builder withSectionNo(Integer sectionNo) {
			this.sectionNo = sectionNo;
			return this;
		}

		public CourseInstanceDto build() {
			return new CourseInstanceDto(this);
		}
	}
	public Integer getId() {
		return id;
	}
	public Integer getCourse_id() {
		return course_id;
	}
	public String getTerm() {
		return term;
	}
	public Integer getSectionNo() {
		return sectionNo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course_id == null) ? 0 : course_id.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((sectionNo == null) ? 0 : sectionNo.hashCode());
		result = prime * result + ((term == null) ? 0 : term.hashCode());
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
		CourseInstanceDto other = (CourseInstanceDto) obj;
		if (course_id == null) {
			if (other.course_id != null)
				return false;
		} else if (!course_id.equals(other.course_id))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sectionNo == null) {
			if (other.sectionNo != null)
				return false;
		} else if (!sectionNo.equals(other.sectionNo))
			return false;
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "CourseInstanceDto [id=" + id + ", course_id=" + course_id + ", term=" + term + ", sectionNo="
				+ sectionNo + "]";
	}
}
