package com.eztech.portal.common.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_CODE_GROUP")
public class TB_CODE_GROUP implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1345910588618303343L;

	@Id
	@Column(name = "C_GRP_CD", nullable = false)
	private String C_GRP_CD;
	
	@Column(name = "C_GRP_CD_NM", nullable = false)
	private String C_GRP_CD_NM;
	
	@Column(name = "C_SYS_DVN_YN", nullable = false)
	private Character C_SYS_DVN_YN;
	
	@Column(name = "C_EXPL")
	private String C_EXPL;
	
	@Column(name = "C_REG_DTM", nullable = false)
	private Date C_REG_DTM;
	
	@Column(name = "C_REG_ID", nullable = false)
	private String C_REG_ID;
	
	@Column(name = "C_MDF_DTM", nullable = false)
	private Date C_MDF_DTM;
	
	@Column(name = "C_MDF_ID", nullable = false)
	private String C_MDF_ID;
	
	@Column(name = "C_USE_YN", nullable = false)
	private Character C_USE_YN;

	public String getC_GRP_CD() {
		return C_GRP_CD;
	}

	public void setC_GRP_CD(String c_GRP_CD) {
		C_GRP_CD = c_GRP_CD;
	}

	public String getC_GRP_CD_NM() {
		return C_GRP_CD_NM;
	}

	public void setC_GRP_CD_NM(String c_GRP_CD_NM) {
		C_GRP_CD_NM = c_GRP_CD_NM;
	}

	public Character getC_SYS_DVN_YN() {
		return C_SYS_DVN_YN;
	}

	public void setC_SYS_DVN_YN(Character c_SYS_DVN_YN) {
		C_SYS_DVN_YN = c_SYS_DVN_YN;
	}

	public String getC_EXPL() {
		return C_EXPL;
	}

	public void setC_EXPL(String c_EXPL) {
		C_EXPL = c_EXPL;
	}

	public Date getC_REG_DTM() {
		return C_REG_DTM;
	}

	public void setC_REG_DTM(Date c_REG_DTM) {
		C_REG_DTM = c_REG_DTM;
	}

	public String getC_REG_ID() {
		return C_REG_ID;
	}

	public void setC_REG_ID(String c_REG_ID) {
		C_REG_ID = c_REG_ID;
	}

	public Date getC_MDF_DTM() {
		return C_MDF_DTM;
	}

	public void setC_MDF_DTM(Date c_MDF_DTM) {
		C_MDF_DTM = c_MDF_DTM;
	}

	public String getC_MDF_ID() {
		return C_MDF_ID;
	}

	public void setC_MDF_ID(String c_MDF_ID) {
		C_MDF_ID = c_MDF_ID;
	}

	public Character getC_USE_YN() {
		return C_USE_YN;
	}

	public void setC_USE_YN(Character c_USE_YN) {
		C_USE_YN = c_USE_YN;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((C_EXPL == null) ? 0 : C_EXPL.hashCode());
		result = prime * result + ((C_GRP_CD == null) ? 0 : C_GRP_CD.hashCode());
		result = prime * result + ((C_GRP_CD_NM == null) ? 0 : C_GRP_CD_NM.hashCode());
		result = prime * result + ((C_MDF_DTM == null) ? 0 : C_MDF_DTM.hashCode());
		result = prime * result + ((C_MDF_ID == null) ? 0 : C_MDF_ID.hashCode());
		result = prime * result + ((C_REG_DTM == null) ? 0 : C_REG_DTM.hashCode());
		result = prime * result + ((C_REG_ID == null) ? 0 : C_REG_ID.hashCode());
		result = prime * result + ((C_SYS_DVN_YN == null) ? 0 : C_SYS_DVN_YN.hashCode());
		result = prime * result + ((C_USE_YN == null) ? 0 : C_USE_YN.hashCode());
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
		TB_CODE_GROUP other = (TB_CODE_GROUP) obj;
		if (C_EXPL == null) {
			if (other.C_EXPL != null)
				return false;
		} else if (!C_EXPL.equals(other.C_EXPL))
			return false;
		if (C_GRP_CD == null) {
			if (other.C_GRP_CD != null)
				return false;
		} else if (!C_GRP_CD.equals(other.C_GRP_CD))
			return false;
		if (C_GRP_CD_NM == null) {
			if (other.C_GRP_CD_NM != null)
				return false;
		} else if (!C_GRP_CD_NM.equals(other.C_GRP_CD_NM))
			return false;
		if (C_MDF_DTM == null) {
			if (other.C_MDF_DTM != null)
				return false;
		} else if (!C_MDF_DTM.equals(other.C_MDF_DTM))
			return false;
		if (C_MDF_ID == null) {
			if (other.C_MDF_ID != null)
				return false;
		} else if (!C_MDF_ID.equals(other.C_MDF_ID))
			return false;
		if (C_REG_DTM == null) {
			if (other.C_REG_DTM != null)
				return false;
		} else if (!C_REG_DTM.equals(other.C_REG_DTM))
			return false;
		if (C_REG_ID == null) {
			if (other.C_REG_ID != null)
				return false;
		} else if (!C_REG_ID.equals(other.C_REG_ID))
			return false;
		if (C_SYS_DVN_YN == null) {
			if (other.C_SYS_DVN_YN != null)
				return false;
		} else if (!C_SYS_DVN_YN.equals(other.C_SYS_DVN_YN))
			return false;
		if (C_USE_YN == null) {
			if (other.C_USE_YN != null)
				return false;
		} else if (!C_USE_YN.equals(other.C_USE_YN))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TB_CODE_GROUP [C_GRP_CD=" + C_GRP_CD + ", C_GRP_CD_NM=" + C_GRP_CD_NM + ", C_SYS_DVN_YN=" + C_SYS_DVN_YN
				+ ", C_EXPL=" + C_EXPL + ", C_REG_DTM=" + C_REG_DTM + ", C_REG_ID=" + C_REG_ID + ", C_MDF_DTM="
				+ C_MDF_DTM + ", C_MDF_ID=" + C_MDF_ID + ", C_USE_YN=" + C_USE_YN + "]";
	}
	
}
