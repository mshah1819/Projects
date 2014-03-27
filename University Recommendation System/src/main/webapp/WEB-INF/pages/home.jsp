<!DOCTYPE html>
<html>
<head>

<title>University</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="" />
<meta name="viewport"
	content="width=device-width, minimum-scale=1.0, maximum-scale=1.0" />

<link href="css/bootstrap.min.css" rel="stylesheet" />
<link href="css/bootstrap-responsive.min.css" rel="stylesheet" />

<!-- <link rel="stylesheet" href="css/jquery-ui-1.8.2.custom.css"
	type="text/css" />
 -->
<!-- <link rel="stylesheet"
		href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" /> -->
		 
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/jquery-ui-1.10.3.custom.css" type="text/css" />
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="css/bootstrap-responsive.min.css" type="text/css" />

<link href="css/style.css" rel="stylesheet" />

<!-- Fav and touch icons -->

<link rel="Juniper Banner" href="img/juniper-banner.jpg" />
<link rel="Juniper Networks" href="img/juniper-networks.jpg" />

<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="img/apple-touch-icon-144-precomposed.png" />

<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="img/apple-touch-icon-114-precomposed.png" />

<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="img/apple-touch-icon-72-precomposed.png" />

<link rel="apple-touch-icon-precomposed"
	href="img/apple-touch-icon-57-precomposed.png" />

<link rel="shortcut icon" href="img/favicon.png" />

<link rel="arrow autosuggest" href="img/arrow.png" />

<link rel="loader autosuggest" href="img/loader.gif" />

<link href="css/jquery-ui.css" rel="stylesheet" />

</head>

<!-- Yahoo API Finance INFO Display AND 4 Boxes on Dash Board Display -->

<style>
  
  #feedback { font-size: 1.4em; }
  #selectable .ui-selecting { background: #FECA40; }
  #selectable .ui-selected { background: #F39814; color: white; }
  #selectable { list-style-type: none; margin: 10px; padding: 3px; width: 600px; }
  #selectable li { margin: 3px; padding: 1px; float: left; width: 250px; height: 150px; font-size: 1.4em; text-align: center; }
  
  
  #selectable-boxes .ui-selecting { background: #FECA40; }
  #selectable-boxes .ui-selected { background: #F39814; color: white; }
  #selectable-boxes { list-style-type: none; margin: 0; padding: 0; width: 800px; }
  #selectable-boxes li { margin: 3px; padding: 1px; float: left; width: 360px; height: 220px; font-size: 1.4em; text-align: center; }
  
  /* Contact List Styling on DASHBOARD PAGE*/
  #contact-list-boxes .ui-selecting { background: #FECA40; }
  #contact-list-boxes .ui-selected { background: #F39814; color: white; }
  #contact-list-boxes { list-style-type: none; margin: 0; padding: 0; width: 800px; }
  #contact-list-boxes li { margin: 3px; padding: 1px; float: left; width: 365px; height: 300px; font-size: 1em; text-align: left; }
 
  
   /* generic table styling */
	table { border-collapse: collapse; }
	th, td { padding: 5px; }
	th { border-bottom: 2px solid #999; background-color: #eee; vertical-align: bottom; }
	td { border-bottom: 1px solid #ccc; }
	/* filter-table specific styling */
	td.alt { background-color: #ffc; background-color: rgba(255, 255, 0, 0.2); } 

</style>

<!-- Google Maps Styling -->

<style type="text/css" media="screen">
	  #site-form-div { width: 200px; float: left; }
	  fieldset { width: 520px; margin-top: 20px}
	  fieldset strong { display: block; margin: 0.5em 0 0em; }
	  fieldset input { width: 95%; }
	  
	  ul span { color: #999; }
</style>

<!-- Tool Tip -->
<style>
	label {
	display: inline-block;
	width: 5em;
	}
</style>

<body>
	<div id="container">
		<div id="content">

			<!-- This will load the handlebars Div -->

		</div>
	</div>
	<script type="text/javascript" src="lib/jquery-1.9.1.min.js"></script>
	<!--  Autocomplete -->
	
	<script type="text/javascript" src="lib/jquery-ui-1.10.3.custom.min.js"></script>
	<script type="text/javascript" src="lib/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="lib/jquery.filtertable.js"></script>

	<script type="text/javascript" src="lib/underscore-min.js"></script>
	<script type="text/javascript" src="lib/handlebars.js"></script>
	<script type="text/javascript" src="lib/backbone-min.js"></script>
	<script src="http://maps.googleapis.com/maps/api/js?sensor=false&amp;libraries=places"></script>
	<script type="text/javascript" src="lib/jquery.geocomplete.js"></script>

	

	<script type="text/javascript" src="lib/bootstrap.min.js"></script>


	<script type="text/javascript" src="lib/scripts.js"></script>

	<script type="text/javascript" src="js/app.js"></script>
</body>
</html>