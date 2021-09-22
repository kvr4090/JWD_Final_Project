<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://localhost:8080/cargo_group/mytag" prefix="custom"%>
<fmt:message key="ru" var="ru"/>
<fmt:message key="eng" var="eng"/>

	<div class="footer">
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-lg-6 ">
					<custom:copyright/>
				</div>
				<div class="col-md-6 col-lg-6 ">
					<div class="footer__social">													
						<a class="md-btn md-btn--outline-primary" href="controller?command=localization&locale=ru">${ru}</a>								
						<a class="md-btn md-btn--outline-primary" href="controller?command=localization&locale=en">${eng}</a>											
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
		
	<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js'></script>
	<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js'></script>
	<script src="./js/scriptPG.js"></script>
	<script src="./js/script.js"></script>
	<script type="text/javascript" src="./assetsForTheme/vendors/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="./assetsForTheme/vendors/imagesloaded/imagesloaded.pkgd.js"></script>
	<script type="text/javascript" src="./assetsForTheme/vendors/isotope-layout/isotope.pkgd.js"></script>
	<script type="text/javascript" src="./assetsForTheme/vendors/jquery-one-page/jquery.nav.min.js"></script>
	<script type="text/javascript" src="./assetsForTheme/vendors/jquery.easing/jquery.easing.min.js"></script>
	<script type="text/javascript" src="./assetsForTheme/vendors/jquery.matchHeight/jquery.matchHeight.min.js"></script>
	<script type="text/javascript" src="./assetsForTheme/vendors/magnific-popup/jquery.magnific-popup.min.js"></script>
	<script type="text/javascript" src="./assetsForTheme/vendors/masonry-layout/masonry.pkgd.js"></script>
	<script type="text/javascript" src="./assetsForTheme/vendors/jquery.waypoints/jquery.waypoints.min.js"></script>
	<script type="text/javascript" src="./assetsForTheme/vendors/swiper/swiper.jquery.js"></script>
	<script type="text/javascript" src="./assetsForTheme/vendors/menu/menu.js"></script>
	<script type="text/javascript" src="./assetsForTheme/vendors/typed/typed.min.js"></script>
	<script type="text/javascript" src="./assetsForTheme/js/main.js"></script>
	<script src="./assetsForModal/js/jquery-3.3.1.min.js"></script>
    <script src="./assetsForModal/js/popper.min.js"></script>
    <script src="./assetsForModal/js/bootstrap.min.js"></script>
    <script src="./assetsForModal/js/main2.js"></script>	
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>
    <script src="./assetsForTheme/js/script.js"></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
	<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js'></script>
	<script src='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js'></script>
	<script src='https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.4.1/js/mdb.min.js'></script>

	</body>
</html>