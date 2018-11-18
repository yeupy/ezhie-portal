package com.eztech.portal.common.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TB_CODE")
public class TB_CODE implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -938747894702635775L;

	@Id
	@ManyToOne
	@JoinColumn(name="C_GRP_CD", nullable = false)
	private TB_CODE_GROUP tb_code_group;

	@Id
	@Column(name = "C_COMN_CD", nullable = false)
	private String C_COMN_CD;

	@Column(name = "C_COMN_CD_NM", nullable = false)
	private String C_COMN_CD_NM;

	@Column(name = "C_SORT_SEQ", nullable = false)
	private Long C_SORT_SEQ;

	@Column(name = "C_EXPL")
	private String C_EXPL;

	@Column(name = "C_OPT_1")
	private String C_OPT_1;

	@Column(name = "C_OPT_2")
	private String C_OPT_2;

	@Column(name = "C_OPT_3")
	private String C_OPT_3;

	@Column(name = "C_OPT_4")
	private String C_OPT_4;

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

	public TB_CODE_GROUP getTb_code_group() {
		return tb_code_group;
	}

	public void setTb_code_group(TB_CODE_GROUP tb_code_group) {
		this.tb_code_group = tb_code_group;
	}

	public String getC_COMN_CD() {
		return C_COMN_CD;
	}

	public void setC_COMN_CD(String c_COMN_CD) {
		C_COMN_CD = c_COMN_CD;
	}

	public String getC_COMN_CD_NM() {
		return C_COMN_CD_NM;
	}

	public void setC_COMN_CD_NM(String c_COMN_CD_NM) {
		C_COMN_CD_NM = c_COMN_CD_NM;
	}

	public Long getC_SORT_SEQ() {
		return C_SORT_SEQ;
	}

	public void setC_SORT_SEQ(Long c_SORT_SEQ) {
		C_SORT_SEQ = c_SORT_SEQ;
	}

	public String getC_EXPL() {
		return C_EXPL;
	}

	public void setC_EXPL(String c_EXPL) {
		C_EXPL = c_EXPL;
	}

	public String getC_OPT_1() {
		return C_OPT_1;
	}

	public void setC_OPT_1(String c_OPT_1) {
		C_OPT_1 = c_OPT_1;
	}

	public String getC_OPT_2() {
		return C_OPT_2;
	}

	public void setC_OPT_2(String c_OPT_2) {
		C_OPT_2 = c_OPT_2;
	}

	public String getC_OPT_3() {
		return C_OPT_3;
	}

	public void setC_OPT_3(String c_OPT_3) {
		C_OPT_3 = c_OPT_3;
	}

	public String getC_OPT_4() {
		return C_OPT_4;
	}

	public void setC_OPT_4(String c_OPT_4) {
		C_OPT_4 = c_OPT_4;
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
		result = prime * result + ((C_COMN_CD == null) ? 0 : C_COMN_CD.hashCode());
		result = prime * result + ((C_COMN_CD_NM == null) ? 0 : C_COMN_CD_NM.hashCode());
		result = prime * result + ((C_EXPL == null) ? 0 : C_EXPL.hashCode());
		result = prime * result + ((C_MDF_DTM == null) ? 0 : C_MDF_DTM.hashCode());
		result = prime * result + ((C_MDF_ID == null) ? 0 : C_MDF_ID.hashCode());
		result = prime * result + ((C_OPT_1 == null) ? 0 : C_OPT_1.hashCode());
		result = prime * result + ((C_OPT_2 == null) ? 0 : C_OPT_2.hashCode());
		result = prime * result + ((C_OPT_3 == null) ? 0 : C_OPT_3.hashCode());
		result = prime * result + ((C_OPT_4 == null) ? 0 : C_OPT_4.hashCode());
		result = prime * result + ((C_REG_DTM == null) ? 0 : C_REG_DTM.hashCode());
		result = prime * result + ((C_REG_ID == null) ? 0 : C_REG_ID.hashCode());
		result = prime * result + ((C_SORT_SEQ == null) ? 0 : C_SORT_SEQ.hashCode());
		result = prime * result + ((C_USE_YN == null) ? 0 : C_USE_YN.hashCode());
		result = prime * result + ((tb_code_group == null) ? 0 : tb_code_group.hashCode());
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
		TB_CODE other = (TB_CODE) obj;
		if (C_COMN_CD == null) {
			if (other.C_COMN_CD != null)
				return false;
		} else if (!C_COMN_CD.equals(other.C_COMN_CD))
			return false;
		if (C_COMN_CD_NM == null) {
			if (other.C_COMN_CD_NM != null)
				return false;
		} else if (!C_COMN_CD_NM.equals(other.C_COMN_CD_NM))
			return false;
		if (C_EXPL == null) {
			if (other.C_EXPL != null)
				return false;
		} else if (!C_EXPL.equals(other.C_EXPL))
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
		if (C_OPT_1 == null) {
			if (other.C_OPT_1 != null)
				return false;
		} else if (!C_OPT_1.equals(other.C_OPT_1))
			return false;
		if (C_OPT_2 == null) {
			if (other.C_OPT_2 != null)
				return false;
		} else if (!C_OPT_2.equals(other.C_OPT_2))
			return false;
		if (C_OPT_3 == null) {
			if (other.C_OPT_3 != null)
				return false;
		} else if (!C_OPT_3.equals(other.C_OPT_3))
			return false;
		if (C_OPT_4 == null) {
			if (other.C_OPT_4 != null)
				return false;
		} else if (!C_OPT_4.equals(other.C_OPT_4))
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
		if (C_SORT_SEQ == null) {
			if (other.C_SORT_SEQ != null)
				return false;
		} else if (!C_SORT_SEQ.equals(other.C_SORT_SEQ))
			return false;
		if (C_USE_YN == null) {
			if (other.C_USE_YN != null)
				return false;
		} else if (!C_USE_YN.equals(other.C_USE_YN))
			return false;
		if (tb_code_group == null) {
			if (other.tb_code_group != null)
				return false;
		} else if (!tb_code_group.equals(other.tb_code_group))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TB_CODE [tb_code_group=" + tb_code_group + ", C_COMN_CD=" + C_COMN_CD + ", C_COMN_CD_NM=" + C_COMN_CD_NM
				+ ", C_SORT_SEQ=" + C_SORT_SEQ + ", C_EXPL=" + C_EXPL + ", C_OPT_1=" + C_OPT_1 + ", C_OPT_2=" + C_OPT_2
				+ ", C_OPT_3=" + C_OPT_3 + ", C_OPT_4=" + C_OPT_4 + ", C_REG_DTM=" + C_REG_DTM + ", C_REG_ID="
				+ C_REG_ID + ", C_MDF_DTM=" + C_MDF_DTM + ", C_MDF_ID=" + C_MDF_ID + ", C_USE_YN=" + C_USE_YN + "]";
	}

}
