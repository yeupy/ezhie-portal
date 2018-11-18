<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="context" scope="session" value="${pageContext.servletContext.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<title>:: HI-SEA 진료정보교류 통합 관리 포털 ::</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<link rel="stylesheet" type="text/css" href="${context}/css/portal/default.css" />
<script type="text/javascript" src="${context}/scripts/portal/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${context}/scripts/portal/design.js"></script>
<script type="text/javascript" src="${context}/scripts/portal/jquery.nicescroll.js"></script>
</head>

<body style="overflow:hidden;" id="body">
<!--skip_menu-->
<p class="skip">
	<a href="#nav">주메뉴로 가기</a>
	<a href="#sub_nav">소메뉴로 가기</a>
	<a href="#contents">본문으로 가기</a>
</p>
<!--/skip_menu-->
	
<!-- body_wrap -->
<div class="body_wrap">
	<!-- header -->
	<div class="header fix">
		<h1 class="logo">
			<a href="#"><img src="${context}/images/portal/main/h1_tit.png" alt="보건복지부 진료정보교류시스템" /></a>
		</h1>

		<div class="gnb_wrap">
			<h2 class="skip">주메뉴</h2>
			<ul class="gnb fix">
				<!-- 최명훈(160712) -->	
				<li class="dep_1 on">
					<a href="../effectiveness/prescription.html" target="iframe">효과분석</a>
					<div>
						<ul class="snb fix">
							<li class="on"><a href="../effectiveness/prescription.html" target="iframe">처방패턴관리</a></li>
							<li><a href="../effectiveness/prescription_1.html" target="iframe">처방패턴관리_다음</a></li>
							<li><a href="#">Menu 1 - 3</a></li>
						</ul>
					</div>
				</li>
				<!-- /최명훈(160712) -->	

				<li class="dep_1">
					<a href="#">개발자도구</a>
					<div>
						<ul class="snb fix">
							<li><a href="#">Menu 2 - 1</a></li>
							<li><a href="#">Menu 2 - 2</a></li>
							<li><a href="#">Menu 2 - 3</a></li>
							<li><a href="#">Menu 2 - 4</a></li>
							<li><a href="#">Menu 2 - 5</a></li>
							<li><a href="#">Menu 2 - 6</a></li>
							<li><a href="#">Menu 2 - 7</a></li>
							<li><a href="#">Menu 2 - 8</a></li>
						</ul>
					</div>
				</li>

				<li class="dep_1">
					<a href="../service/audit_record.html" target="iframe">서비스관리</a>
					<div>
						<ul class="snb fix">
							<li><a href="../service/audit_record.html" target="iframe">Audit Record<br />(admin)</a></li>
							<li><a href="../service/audit_record_user.html" target="iframe">Audit Record (user)</a></li>
							<!-- 최명훈(160615) -->
							<li><a href="../service/q_a.html" target="iframe">Q&amp;A</a></li>
							<!-- /최명훈(160615) -->
							<li><a href="../service/testview.html" target="iframe">자동완성 테스트</a></li>
						</ul>
					</div>
				</li>

				<li class="dep_1">
					<a href="${context}/portal/system/getHproviderList.do" target="iframe">시스템관리</a>
					<div>
						<ul class="snb fix">
							<li><a href="<c:out value="${context}/portal/system/getHproviderList.do"/>" target="iframe">의료기관 관리</a></li>
							<li><a href="../system/user.html" target="iframe">사용자 관리</a></li>
							<li><a href="../system/authority.html" target="iframe">권한 관리</a></li>
							<li><a href="#">코드 관리</a></li>
							<li><a href="../system/oid.html" target="iframe">OID 관리</a></li>
							<li><a href="../system/medical_care.html" target="iframe">요양기관번호 관리</a></li>
							<!-- 최명훈(160713) -->
							<li><a href="../system/registry.html" target="iframe">레지스트리 관리</a></li>
							<!-- /최명훈(160713) -->
						</ul>
					</div>
				</li>

				<li class="dep_1">
					<a href="#">사용자정보</a>
					<div class="dep_1_5">
						<ul class="snb fix">
							<li><a href="#">Menu 5 - 1</a></li>
							<li><a href="#">Menu 5 - 2</a></li>
							<li><a href="#">Menu 5 - 3</a></li>
							<li><a href="#">Menu 5 - 4</a></li>
							<li><a href="#">Menu 5 - 5</a></li>
						</ul>
					</div>
				</li>
			</ul>
			<!--<div class="bg_gnb_wrap"></div>--> <!-- 최명훈(160628) -->
		</div>

		<div class="util_wrap">
			<ul class="util_menu fix">
				<!-- 최명훈 (160705) -->
				<li><a href="#" class="btn_logout"><img src="${context}/images/portal/main/bg_logout.png" alt="로그아웃" /></a></li>
				<!-- /최명훈 (160705) -->
				<li>
					<a href="#" class="btn_pro"><img src="${context}/images/portal/main/btn_profile.png" alt="사용자 정보" /></a>
					<div class="profile_wrap">
						<div class="profile_info_wrap">
							<p class="profile_info">
								<strong>관리자님</strong><br />
								<span><em>현재시간</em> 2016.03.02. 10:30</span>
							</p>

							<ul class="btn_theme fix">
								<li class="on"><a href="#" class="blue"></a></li>
								<li><a href="#" class="green"></a></li>
								<li><a href="#" class="orange"></a></li>
								<li><a href="#" class="red"></a></li>
								<li><a href="#" class="purple"></a></li><!-- 최명훈 (160617) - 1컬러 추가 -->
							</ul>
						</div>

						<p class="btn_close"><a href="#">Close</a></p>
					</div>
				</li>

				<li>
					<a href="#" class="open_menu"><img src="${context}/images/portal/main/btn_fullmenu.png" alt="전체메뉴 버튼" /></a>
				</li>
			</ul>
		</div>
	</div>
	<!-- /header -->

	<!-- container -->
	<div class="container">
		<div class="content" style="background:#F1F3F6; position:relative;">
			<h3 class="skip"></h3>
			<iframe id="iframe" name="iframe" src="../effectiveness/prescription.html" style="width:100%;" frameBorder="0" ></iframe>
			<script type="text/javascript" src="${context}/scripts/portal/chk_color.js"></script>
		</div>
	</div>
	<!-- /container -->
	
	<!-- footer -->
	<div class="footer">
		<div class="footer_content">
			
		</div>
	</div>
	<!-- /footer -->

	<div id="myToggle" class="total_menu_section" >
	<!-- full menu -->	
		<div class="full_menu">
			<div class="full_title fix">
				<strong>진료정보교류시스템</strong>
				<a href="#" class="full_close"><img src="${context}/images/portal/main/btn_full_close.gif"alt="닫기" /></a>
			</div>

			<div class="full_content">
				<ul class="full_menu_list">
					<!-- 최명훈(160712) -->	
					<li class="full_depth1_li"><a href="../effectiveness/prescription.html" target="iframe">효과분석</a>
						<ul>
							<li class="on"><a href="../effectiveness/prescription.html" target="iframe">처방패턴관리</a></li>
							<li><a href="../effectiveness/prescription1.html" target="iframe">처방패턴관리_다음</a></li>
							<li><a href="#">Menu 1 - 3</a></li>
						</ul>
					</li>
					<!-- /최명훈(160712) -->

					<li class="full_depth1_li"><a href="#">개발자 도구</a>
						<ul>
							<li><a href="#">Menu 2 - 1</a></li>
							<li><a href="#">Menu 2 - 2</a></li>
							<li><a href="#">Menu 2 - 3</a></li>
							<li><a href="#">Menu 2 - 4</a></li>
							<li><a href="#">Menu 2 - 5</a></li>
							<li><a href="#">Menu 2 - 6</a></li>
							<li><a href="#">Menu 2 - 7</a></li>
							<li><a href="#">Menu 2 - 8</a></li>
						</ul>
					</li>

					<li class="full_depth1_li"><a href="#">서비스 관리</a>
						<ul>
							<li><a href="../service/audit_record.html" target="iframe">Audit Record(admin)</a></li>
							<li><a href="../service/audit_record_user.html" target="iframe">Audit Record (user)</a></li>
							<!-- 최명훈(160615) -->
							<li><a href="../service/q_a.html" target="iframe">Q&amp;A</a></li>
							<!-- /최명훈(160615) -->
						</ul>
					</li>

					<li class="full_depth1_li"><a href="#">시스템 관리</a>
						<ul>
							<li><a href="../system/medical_institution.html" target="iframe">의료기관 관리</a></li>
							<li><a href="../system/user.html" target="iframe">사용자 관리</a></li>
							<li><a href="../system/authority.html" target="iframe">권한 관리</a></li>
							<li><a href="#">코드 관리</a></li>
							<li><a href="../system/oid.html" target="iframe">OID 관리</a></li>
							<li><a href="../system/medical_care.html" target="iframe">요양기관번호 관리</a></li>
							<!-- 최명훈(160713) -->
							<li><a href="../system/registry.html" target="iframe">레지스트리 관리</a></li>
							<!-- /최명훈(160713) -->
						</ul>
					</li>

					<li class="full_depth1_li"><a href="#">사용자 정보</a>
						<ul>
							<li><a href="#">Menu 5 - 1</a></li>
							<li><a href="#">Menu 5 - 2</a></li>
							<li><a href="#">Menu 5 - 3</a></li>
							<li><a href="#">Menu 5 - 4</a></li>
							<li><a href="#">Menu 5 - 5</a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	<!-- /full menu -->
	</div>
</div>
<!-- /body_wrap -->

</body>
</html>