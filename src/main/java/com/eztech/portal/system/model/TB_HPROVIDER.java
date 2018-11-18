package com.eztech.portal.system.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import com.eztech.portal.common.model.TB_CODE;

@Entity
@Table(name = "TB_HPROVIDER")
@SequenceGenerator(name = "sequence", sequenceName = "SQ_MDINS_ID", allocationSize = 1)
@DynamicUpdate
@SelectBeforeUpdate
public class TB_HPROVIDER implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3390866007288492076L;

	@Id
	@Column(name = "C_MDINS_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	private Long C_MDINS_ID;								// 의료기관 식별번호

	@Column(name = "C_APFR_ID")
	private Long C_APFR_ID;									// 신청서 식별번호

	@Column(name = "C_MDINS_OID", nullable = false)
	private String C_MDINS_OID;								// 의료기관 OID

	@Column(name = "C_MDINS_NM")
	private String C_MDINS_NM;								// 의료기관 명				

	@Column(name = "C_RPST_NM")
	private String C_RPST_NM;								// 대표자 명

	@Column(name = "C_ASDP_NM")
	private String C_ASDP_NM;								// 담당자 명

	@Column(name = "C_ASDP_EMAL_ACCT")
	private String C_ASDP_EMAL_ACCT; 						// 담당자 이메일 계정

	@Column(name = "C_ASDP_EMAL_DMN")
	private String C_ASDP_EMAL_DMN; 						// 담당자 이메일 도메인

	@Column(name = "C_TEL_NO_1")
	private String C_TEL_NO_1; 								// 전화번호 1

	@Column(name = "C_TEL_NO_2")
	private String C_TEL_NO_2; 								// 전화번호 2

	@Column(name = "C_TEL_NO_3")
	private String C_TEL_NO_3; 								// 전화번호 3

	@Column(name = "C_MDINS_POST_NO", nullable = false)
	private String C_MDINS_POST_NO; 						// 의료기관 우편번호

	@Column(name = "C_MDINS_ADDR")
	private String C_MDINS_ADDR; 							// 의료기관 주소

	@Column(name = "C_MDINS_ADDR_DTL")
	private String C_MDINS_ADDR_DTL; 						// 의료기관 상세주소

	@Column(name = "C_RCORG_NO", nullable = false)
	private String C_RCORG_NO; 								// 요양기관 번호

	@Column(name = "C_MDINS_CLS_CD", nullable = false)
	private String C_MDINS_CLS_CD; 							// 의료기관 유형코드

	@Column(name = "C_DR_CNT_CNTE")
	private String C_DR_CNT_CNTE; 							// 의사 수 내용

	@Column(name = "C_RPG_OID")
	private String C_RPG_OID; 								// 레파지토리 OID

	@Column(name = "C_RGSY_OID")
	private String C_RGSY_OID; 								// 레지스트리 OID

	@Column(name = "C_SPNT_CLS_CD")
	private String C_SPNT_CLS_CD; 							// 거점 OID

	@Column(name = "C_VNDR_OID")
	private String C_VNDR_OID; 								// 벤더 OID

	@Column(name = "C_VNDR_NM")
	private String C_VNDR_NM; 								// 벤더 명

	@Column(name = "C_ENTY_DT")
	private Date C_ENTY_DT; 								// 가입일자

	@Column(name = "C_COC_DT")
	private Date C_COC_DT; 									// 해지일자

	@Column(name = "C_ENTY_STS_CD", nullable = false)
	private String C_ENTY_STS_CD; 							// 가입상태 코드

	@Column(name = "C_EMR_SYS_CLS_YN", nullable = false)
	private Character C_EMR_SYS_CLS_YN; 					// EMR 시스템 유형여부

	@Column(name = "C_EMR_SYS_CD")
	private String C_EMR_SYS_CD; 							// EMR 시스템 코드

	@Column(name = "C_DMD_SYS_CLS_YN", nullable = false)
	private Character C_DMD_SYS_CLS_YN; 					// 청구 시스템 유형여부

	@Column(name = "C_DMD_SYS_CD")
	private String C_DMD_SYS_CD; 							// 청구 시스템 코드

	@Column(name = "C_RCPC_SRV_URL")
	private String C_RCPC_SRV_URL; 							// 수신처 서비스 URL

	@Column(name = "C_CERT_DATA_CNTE")
	private String C_CERT_DATA_CNTE; 						// 인증서 경로

	@Column(name = "C_CERT_FILE_NM")
	private String C_CERT_FILE_NM; 							// 인증서 파일명

	@Column(name = "C_CERT_EXPR_DTM")
	private Date C_CERT_EXPR_DTM; 							// 인증서 만료일시

	@Column(name = "C_REG_DTM", nullable = false)
	private Date C_REG_DTM; 								// 등록 일시

	@Column(name = "C_REG_ID", nullable = false)
	private String C_REG_ID; 								// 등록 아이디

	@Column(name = "C_MDF_DTM", nullable = false)
	private Date C_MDF_DTM; 								// 수정 일시

	@Column(name = "C_MDF_ID", nullable = false)
	private String C_MDF_ID; 								// 수정 아이디

	@Column(name = "C_USE_YN", nullable = false)
	private Character C_USE_YN; 							// 사용 여부

	public Long getC_MDINS_ID() {
		return C_MDINS_ID;
	}

	public void setC_MDINS_ID(Long c_MDINS_ID) {
		C_MDINS_ID = c_MDINS_ID;
	}

	public Long getC_APFR_ID() {
		return C_APFR_ID;
	}

	public void setC_APFR_ID(Long c_APFR_ID) {
		C_APFR_ID = c_APFR_ID;
	}

	public String getC_MDINS_OID() {
		return C_MDINS_OID;
	}

	public void setC_MDINS_OID(String c_MDINS_OID) {
		C_MDINS_OID = c_MDINS_OID;
	}

	public String getC_MDINS_NM() {
		return C_MDINS_NM;
	}

	public void setC_MDINS_NM(String c_MDINS_NM) {
		C_MDINS_NM = c_MDINS_NM;
	}

	public String getC_RPST_NM() {
		return C_RPST_NM;
	}

	public void setC_RPST_NM(String c_RPST_NM) {
		C_RPST_NM = c_RPST_NM;
	}

	public String getC_ASDP_NM() {
		return C_ASDP_NM;
	}

	public void setC_ASDP_NM(String c_ASDP_NM) {
		C_ASDP_NM = c_ASDP_NM;
	}

	public String getC_ASDP_EMAL_ACCT() {
		return C_ASDP_EMAL_ACCT;
	}

	public void setC_ASDP_EMAL_ACCT(String c_ASDP_EMAL_ACCT) {
		C_ASDP_EMAL_ACCT = c_ASDP_EMAL_ACCT;
	}

	public String getC_ASDP_EMAL_DMN() {
		return C_ASDP_EMAL_DMN;
	}

	public void setC_ASDP_EMAL_DMN(String c_ASDP_EMAL_DMN) {
		C_ASDP_EMAL_DMN = c_ASDP_EMAL_DMN;
	}

	public String getC_TEL_NO_1() {
		return C_TEL_NO_1;
	}

	public void setC_TEL_NO_1(String c_TEL_NO_1) {
		C_TEL_NO_1 = c_TEL_NO_1;
	}

	public String getC_TEL_NO_2() {
		return C_TEL_NO_2;
	}

	public void setC_TEL_NO_2(String c_TEL_NO_2) {
		C_TEL_NO_2 = c_TEL_NO_2;
	}

	public String getC_TEL_NO_3() {
		return C_TEL_NO_3;
	}

	public void setC_TEL_NO_3(String c_TEL_NO_3) {
		C_TEL_NO_3 = c_TEL_NO_3;
	}

	public String getC_MDINS_POST_NO() {
		return C_MDINS_POST_NO;
	}

	public void setC_MDINS_POST_NO(String c_MDINS_POST_NO) {
		C_MDINS_POST_NO = c_MDINS_POST_NO;
	}

	public String getC_MDINS_ADDR() {
		return C_MDINS_ADDR;
	}

	public void setC_MDINS_ADDR(String c_MDINS_ADDR) {
		C_MDINS_ADDR = c_MDINS_ADDR;
	}

	public String getC_MDINS_ADDR_DTL() {
		return C_MDINS_ADDR_DTL;
	}

	public void setC_MDINS_ADDR_DTL(String c_MDINS_ADDR_DTL) {
		C_MDINS_ADDR_DTL = c_MDINS_ADDR_DTL;
	}

	public String getC_RCORG_NO() {
		return C_RCORG_NO;
	}

	public void setC_RCORG_NO(String c_RCORG_NO) {
		C_RCORG_NO = c_RCORG_NO;
	}

	public String getC_MDINS_CLS_CD() {
		return C_MDINS_CLS_CD;
	}

	public void setC_MDINS_CLS_CD(String c_MDINS_CLS_CD) {
		C_MDINS_CLS_CD = c_MDINS_CLS_CD;
	}

	public String getC_DR_CNT_CNTE() {
		return C_DR_CNT_CNTE;
	}

	public void setC_DR_CNT_CNTE(String c_DR_CNT_CNTE) {
		C_DR_CNT_CNTE = c_DR_CNT_CNTE;
	}

	public String getC_RPG_OID() {
		return C_RPG_OID;
	}

	public void setC_RPG_OID(String c_RPG_OID) {
		C_RPG_OID = c_RPG_OID;
	}

	public String getC_RGSY_OID() {
		return C_RGSY_OID;
	}

	public void setC_RGSY_OID(String c_RGSY_OID) {
		C_RGSY_OID = c_RGSY_OID;
	}

	public String getC_SPNT_CLS_CD() {
		return C_SPNT_CLS_CD;
	}

	public void setC_SPNT_CLS_CD(String c_SPNT_CLS_CD) {
		C_SPNT_CLS_CD = c_SPNT_CLS_CD;
	}

	public String getC_VNDR_OID() {
		return C_VNDR_OID;
	}

	public void setC_VNDR_OID(String c_VNDR_OID) {
		C_VNDR_OID = c_VNDR_OID;
	}

	public String getC_VNDR_NM() {
		return C_VNDR_NM;
	}

	public void setC_VNDR_NM(String c_VNDR_NM) {
		C_VNDR_NM = c_VNDR_NM;
	}

	public Date getC_ENTY_DT() {
		return C_ENTY_DT;
	}

	public void setC_ENTY_DT(Date c_ENTY_DT) {
		C_ENTY_DT = c_ENTY_DT;
	}

	public Date getC_COC_DT() {
		return C_COC_DT;
	}

	public void setC_COC_DT(Date c_COC_DT) {
		C_COC_DT = c_COC_DT;
	}

	public String getC_ENTY_STS_CD() {
		return C_ENTY_STS_CD;
	}

	public void setC_ENTY_STS_CD(String c_ENTY_STS_CD) {
		C_ENTY_STS_CD = c_ENTY_STS_CD;
	}

	public Character getC_EMR_SYS_CLS_YN() {
		return C_EMR_SYS_CLS_YN;
	}

	public void setC_EMR_SYS_CLS_YN(Character c_EMR_SYS_CLS_YN) {
		C_EMR_SYS_CLS_YN = c_EMR_SYS_CLS_YN;
	}

	public String getC_EMR_SYS_CD() {
		return C_EMR_SYS_CD;
	}

	public void setC_EMR_SYS_CD(String c_EMR_SYS_CD) {
		C_EMR_SYS_CD = c_EMR_SYS_CD;
	}

	public Character getC_DMD_SYS_CLS_YN() {
		return C_DMD_SYS_CLS_YN;
	}

	public void setC_DMD_SYS_CLS_YN(Character c_DMD_SYS_CLS_YN) {
		C_DMD_SYS_CLS_YN = c_DMD_SYS_CLS_YN;
	}

	public String getC_DMD_SYS_CD() {
		return C_DMD_SYS_CD;
	}

	public void setC_DMD_SYS_CD(String c_DMD_SYS_CD) {
		C_DMD_SYS_CD = c_DMD_SYS_CD;
	}

	public String getC_RCPC_SRV_URL() {
		return C_RCPC_SRV_URL;
	}

	public void setC_RCPC_SRV_URL(String c_RCPC_SRV_URL) {
		C_RCPC_SRV_URL = c_RCPC_SRV_URL;
	}

	public String getC_CERT_DATA_CNTE() {
		return C_CERT_DATA_CNTE;
	}

	public void setC_CERT_DATA_CNTE(String c_CERT_DATA_CNTE) {
		C_CERT_DATA_CNTE = c_CERT_DATA_CNTE;
	}

	public String getC_CERT_FILE_NM() {
		return C_CERT_FILE_NM;
	}

	public void setC_CERT_FILE_NM(String c_CERT_FILE_NM) {
		C_CERT_FILE_NM = c_CERT_FILE_NM;
	}

	public Date getC_CERT_EXPR_DTM() {
		return C_CERT_EXPR_DTM;
	}

	public void setC_CERT_EXPR_DTM(Date c_CERT_EXPR_DTM) {
		C_CERT_EXPR_DTM = c_CERT_EXPR_DTM;
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
		result = prime * result + ((C_APFR_ID == null) ? 0 : C_APFR_ID.hashCode());
		result = prime * result + ((C_ASDP_EMAL_ACCT == null) ? 0 : C_ASDP_EMAL_ACCT.hashCode());
		result = prime * result + ((C_ASDP_EMAL_DMN == null) ? 0 : C_ASDP_EMAL_DMN.hashCode());
		result = prime * result + ((C_ASDP_NM == null) ? 0 : C_ASDP_NM.hashCode());
		result = prime * result + ((C_CERT_DATA_CNTE == null) ? 0 : C_CERT_DATA_CNTE.hashCode());
		result = prime * result + ((C_CERT_EXPR_DTM == null) ? 0 : C_CERT_EXPR_DTM.hashCode());
		result = prime * result + ((C_CERT_FILE_NM == null) ? 0 : C_CERT_FILE_NM.hashCode());
		result = prime * result + ((C_COC_DT == null) ? 0 : C_COC_DT.hashCode());
		result = prime * result + ((C_DMD_SYS_CD == null) ? 0 : C_DMD_SYS_CD.hashCode());
		result = prime * result + ((C_DMD_SYS_CLS_YN == null) ? 0 : C_DMD_SYS_CLS_YN.hashCode());
		result = prime * result + ((C_DR_CNT_CNTE == null) ? 0 : C_DR_CNT_CNTE.hashCode());
		result = prime * result + ((C_EMR_SYS_CD == null) ? 0 : C_EMR_SYS_CD.hashCode());
		result = prime * result + ((C_EMR_SYS_CLS_YN == null) ? 0 : C_EMR_SYS_CLS_YN.hashCode());
		result = prime * result + ((C_ENTY_DT == null) ? 0 : C_ENTY_DT.hashCode());
		result = prime * result + ((C_ENTY_STS_CD == null) ? 0 : C_ENTY_STS_CD.hashCode());
		result = prime * result + ((C_MDF_DTM == null) ? 0 : C_MDF_DTM.hashCode());
		result = prime * result + ((C_MDF_ID == null) ? 0 : C_MDF_ID.hashCode());
		result = prime * result + ((C_MDINS_ADDR == null) ? 0 : C_MDINS_ADDR.hashCode());
		result = prime * result + ((C_MDINS_ADDR_DTL == null) ? 0 : C_MDINS_ADDR_DTL.hashCode());
		result = prime * result + ((C_MDINS_CLS_CD == null) ? 0 : C_MDINS_CLS_CD.hashCode());
		result = prime * result + ((C_MDINS_ID == null) ? 0 : C_MDINS_ID.hashCode());
		result = prime * result + ((C_MDINS_NM == null) ? 0 : C_MDINS_NM.hashCode());
		result = prime * result + ((C_MDINS_OID == null) ? 0 : C_MDINS_OID.hashCode());
		result = prime * result + ((C_MDINS_POST_NO == null) ? 0 : C_MDINS_POST_NO.hashCode());
		result = prime * result + ((C_RCORG_NO == null) ? 0 : C_RCORG_NO.hashCode());
		result = prime * result + ((C_RCPC_SRV_URL == null) ? 0 : C_RCPC_SRV_URL.hashCode());
		result = prime * result + ((C_REG_DTM == null) ? 0 : C_REG_DTM.hashCode());
		result = prime * result + ((C_REG_ID == null) ? 0 : C_REG_ID.hashCode());
		result = prime * result + ((C_RGSY_OID == null) ? 0 : C_RGSY_OID.hashCode());
		result = prime * result + ((C_RPG_OID == null) ? 0 : C_RPG_OID.hashCode());
		result = prime * result + ((C_RPST_NM == null) ? 0 : C_RPST_NM.hashCode());
		result = prime * result + ((C_SPNT_CLS_CD == null) ? 0 : C_SPNT_CLS_CD.hashCode());
		result = prime * result + ((C_TEL_NO_1 == null) ? 0 : C_TEL_NO_1.hashCode());
		result = prime * result + ((C_TEL_NO_2 == null) ? 0 : C_TEL_NO_2.hashCode());
		result = prime * result + ((C_TEL_NO_3 == null) ? 0 : C_TEL_NO_3.hashCode());
		result = prime * result + ((C_USE_YN == null) ? 0 : C_USE_YN.hashCode());
		result = prime * result + ((C_VNDR_NM == null) ? 0 : C_VNDR_NM.hashCode());
		result = prime * result + ((C_VNDR_OID == null) ? 0 : C_VNDR_OID.hashCode());
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
		TB_HPROVIDER other = (TB_HPROVIDER) obj;
		if (C_APFR_ID == null) {
			if (other.C_APFR_ID != null)
				return false;
		} else if (!C_APFR_ID.equals(other.C_APFR_ID))
			return false;
		if (C_ASDP_EMAL_ACCT == null) {
			if (other.C_ASDP_EMAL_ACCT != null)
				return false;
		} else if (!C_ASDP_EMAL_ACCT.equals(other.C_ASDP_EMAL_ACCT))
			return false;
		if (C_ASDP_EMAL_DMN == null) {
			if (other.C_ASDP_EMAL_DMN != null)
				return false;
		} else if (!C_ASDP_EMAL_DMN.equals(other.C_ASDP_EMAL_DMN))
			return false;
		if (C_ASDP_NM == null) {
			if (other.C_ASDP_NM != null)
				return false;
		} else if (!C_ASDP_NM.equals(other.C_ASDP_NM))
			return false;
		if (C_CERT_DATA_CNTE == null) {
			if (other.C_CERT_DATA_CNTE != null)
				return false;
		} else if (!C_CERT_DATA_CNTE.equals(other.C_CERT_DATA_CNTE))
			return false;
		if (C_CERT_EXPR_DTM == null) {
			if (other.C_CERT_EXPR_DTM != null)
				return false;
		} else if (!C_CERT_EXPR_DTM.equals(other.C_CERT_EXPR_DTM))
			return false;
		if (C_CERT_FILE_NM == null) {
			if (other.C_CERT_FILE_NM != null)
				return false;
		} else if (!C_CERT_FILE_NM.equals(other.C_CERT_FILE_NM))
			return false;
		if (C_COC_DT == null) {
			if (other.C_COC_DT != null)
				return false;
		} else if (!C_COC_DT.equals(other.C_COC_DT))
			return false;
		if (C_DMD_SYS_CD == null) {
			if (other.C_DMD_SYS_CD != null)
				return false;
		} else if (!C_DMD_SYS_CD.equals(other.C_DMD_SYS_CD))
			return false;
		if (C_DMD_SYS_CLS_YN == null) {
			if (other.C_DMD_SYS_CLS_YN != null)
				return false;
		} else if (!C_DMD_SYS_CLS_YN.equals(other.C_DMD_SYS_CLS_YN))
			return false;
		if (C_DR_CNT_CNTE == null) {
			if (other.C_DR_CNT_CNTE != null)
				return false;
		} else if (!C_DR_CNT_CNTE.equals(other.C_DR_CNT_CNTE))
			return false;
		if (C_EMR_SYS_CD == null) {
			if (other.C_EMR_SYS_CD != null)
				return false;
		} else if (!C_EMR_SYS_CD.equals(other.C_EMR_SYS_CD))
			return false;
		if (C_EMR_SYS_CLS_YN == null) {
			if (other.C_EMR_SYS_CLS_YN != null)
				return false;
		} else if (!C_EMR_SYS_CLS_YN.equals(other.C_EMR_SYS_CLS_YN))
			return false;
		if (C_ENTY_DT == null) {
			if (other.C_ENTY_DT != null)
				return false;
		} else if (!C_ENTY_DT.equals(other.C_ENTY_DT))
			return false;
		if (C_ENTY_STS_CD == null) {
			if (other.C_ENTY_STS_CD != null)
				return false;
		} else if (!C_ENTY_STS_CD.equals(other.C_ENTY_STS_CD))
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
		if (C_MDINS_ADDR == null) {
			if (other.C_MDINS_ADDR != null)
				return false;
		} else if (!C_MDINS_ADDR.equals(other.C_MDINS_ADDR))
			return false;
		if (C_MDINS_ADDR_DTL == null) {
			if (other.C_MDINS_ADDR_DTL != null)
				return false;
		} else if (!C_MDINS_ADDR_DTL.equals(other.C_MDINS_ADDR_DTL))
			return false;
		if (C_MDINS_CLS_CD == null) {
			if (other.C_MDINS_CLS_CD != null)
				return false;
		} else if (!C_MDINS_CLS_CD.equals(other.C_MDINS_CLS_CD))
			return false;
		if (C_MDINS_ID == null) {
			if (other.C_MDINS_ID != null)
				return false;
		} else if (!C_MDINS_ID.equals(other.C_MDINS_ID))
			return false;
		if (C_MDINS_NM == null) {
			if (other.C_MDINS_NM != null)
				return false;
		} else if (!C_MDINS_NM.equals(other.C_MDINS_NM))
			return false;
		if (C_MDINS_OID == null) {
			if (other.C_MDINS_OID != null)
				return false;
		} else if (!C_MDINS_OID.equals(other.C_MDINS_OID))
			return false;
		if (C_MDINS_POST_NO == null) {
			if (other.C_MDINS_POST_NO != null)
				return false;
		} else if (!C_MDINS_POST_NO.equals(other.C_MDINS_POST_NO))
			return false;
		if (C_RCORG_NO == null) {
			if (other.C_RCORG_NO != null)
				return false;
		} else if (!C_RCORG_NO.equals(other.C_RCORG_NO))
			return false;
		if (C_RCPC_SRV_URL == null) {
			if (other.C_RCPC_SRV_URL != null)
				return false;
		} else if (!C_RCPC_SRV_URL.equals(other.C_RCPC_SRV_URL))
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
		if (C_RGSY_OID == null) {
			if (other.C_RGSY_OID != null)
				return false;
		} else if (!C_RGSY_OID.equals(other.C_RGSY_OID))
			return false;
		if (C_RPG_OID == null) {
			if (other.C_RPG_OID != null)
				return false;
		} else if (!C_RPG_OID.equals(other.C_RPG_OID))
			return false;
		if (C_RPST_NM == null) {
			if (other.C_RPST_NM != null)
				return false;
		} else if (!C_RPST_NM.equals(other.C_RPST_NM))
			return false;
		if (C_SPNT_CLS_CD == null) {
			if (other.C_SPNT_CLS_CD != null)
				return false;
		} else if (!C_SPNT_CLS_CD.equals(other.C_SPNT_CLS_CD))
			return false;
		if (C_TEL_NO_1 == null) {
			if (other.C_TEL_NO_1 != null)
				return false;
		} else if (!C_TEL_NO_1.equals(other.C_TEL_NO_1))
			return false;
		if (C_TEL_NO_2 == null) {
			if (other.C_TEL_NO_2 != null)
				return false;
		} else if (!C_TEL_NO_2.equals(other.C_TEL_NO_2))
			return false;
		if (C_TEL_NO_3 == null) {
			if (other.C_TEL_NO_3 != null)
				return false;
		} else if (!C_TEL_NO_3.equals(other.C_TEL_NO_3))
			return false;
		if (C_USE_YN == null) {
			if (other.C_USE_YN != null)
				return false;
		} else if (!C_USE_YN.equals(other.C_USE_YN))
			return false;
		if (C_VNDR_NM == null) {
			if (other.C_VNDR_NM != null)
				return false;
		} else if (!C_VNDR_NM.equals(other.C_VNDR_NM))
			return false;
		if (C_VNDR_OID == null) {
			if (other.C_VNDR_OID != null)
				return false;
		} else if (!C_VNDR_OID.equals(other.C_VNDR_OID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TB_HPROVIDER [C_MDINS_ID=" + C_MDINS_ID + ", C_APFR_ID=" + C_APFR_ID + ", C_MDINS_OID=" + C_MDINS_OID
				+ ", C_MDINS_NM=" + C_MDINS_NM + ", C_RPST_NM=" + C_RPST_NM + ", C_ASDP_NM=" + C_ASDP_NM
				+ ", C_ASDP_EMAL_ACCT=" + C_ASDP_EMAL_ACCT + ", C_ASDP_EMAL_DMN=" + C_ASDP_EMAL_DMN + ", C_TEL_NO_1="
				+ C_TEL_NO_1 + ", C_TEL_NO_2=" + C_TEL_NO_2 + ", C_TEL_NO_3=" + C_TEL_NO_3 + ", C_MDINS_POST_NO="
				+ C_MDINS_POST_NO + ", C_MDINS_ADDR=" + C_MDINS_ADDR + ", C_MDINS_ADDR_DTL=" + C_MDINS_ADDR_DTL
				+ ", C_RCORG_NO=" + C_RCORG_NO + ", C_MDINS_CLS_CD=" + C_MDINS_CLS_CD + ", C_DR_CNT_CNTE="
				+ C_DR_CNT_CNTE + ", C_RPG_OID=" + C_RPG_OID + ", C_RGSY_OID=" + C_RGSY_OID + ", C_SPNT_CLS_CD="
				+ C_SPNT_CLS_CD + ", C_VNDR_OID=" + C_VNDR_OID + ", C_VNDR_NM=" + C_VNDR_NM + ", C_ENTY_DT=" + C_ENTY_DT
				+ ", C_COC_DT=" + C_COC_DT + ", C_ENTY_STS_CD=" + C_ENTY_STS_CD + ", C_EMR_SYS_CLS_YN="
				+ C_EMR_SYS_CLS_YN + ", C_EMR_SYS_CD=" + C_EMR_SYS_CD + ", C_DMD_SYS_CLS_YN=" + C_DMD_SYS_CLS_YN
				+ ", C_DMD_SYS_CD=" + C_DMD_SYS_CD + ", C_RCPC_SRV_URL=" + C_RCPC_SRV_URL + ", C_CERT_DATA_CNTE="
				+ C_CERT_DATA_CNTE + ", C_CERT_FILE_NM=" + C_CERT_FILE_NM + ", C_CERT_EXPR_DTM=" + C_CERT_EXPR_DTM
				+ ", C_REG_DTM=" + C_REG_DTM + ", C_REG_ID=" + C_REG_ID + ", C_MDF_DTM=" + C_MDF_DTM + ", C_MDF_ID="
				+ C_MDF_ID + ", C_USE_YN=" + C_USE_YN + "]";
	}

}
