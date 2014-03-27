/**
 * app.js
 * Backbone MVC architecture in one file
 * @author skshirsagar
 *
 **/
"use strict";

//Creating the application namespace
var id, inputString, customerInfo, customerJSON;
var Customer360App = {
		models: {},
		views: {},
		utils: {},
};



//-------------------------------------------------- Utilities ---------------------------------------------------- //

//The Template Loader. Used to asynchronously load templates located in separate .html files

Customer360App.utils.templateLoader = {


		templates: {},

		load: function(names, callback) {
			console.log("template loader");
			var deferreds = [],
			self = this;

			$.each(names, function(index, name) {
				console.log('tpl/' + name + '.handlebars');
				deferreds.push($.ajax({url:'tpl/' + name + '.handlebars',contentType:'text/x-handlebars-template; charset=UTF-8', success: function(data) {
					//console.log(data);
					this.source = data;
					//console.log(this.source);
					self.templates[name] = Handlebars.compile(this.source);
				}}));
			});

			$.when.apply(null, deferreds).done(callback);
		},

		// Get template by name from hash of preloaded templates
		get: function(name) {
			return this.templates[name];
		}

};


//-------------------------------------------------- The Models ---------------------------------------------------- //

Customer360App.models.Transition = Backbone.Model.extend({

	url: function() {
		console.log("model.url");
		return this.url;
	},
	setURL: function(restURL) {
		console.log("model.seturl");
		this.url = restURL;
	},

	parse: function(response) {
		console.log("model.parse");
		return response;
	}
});



//-------------------------------------------------- The Views ---------------------------------------------------- //

//Customer Search page

Customer360App.views.SearchCustomer = Backbone.View.extend({

	initialize: function() {
		this.template = Customer360App.utils.templateLoader.get('search-page-template');
	},

	render: function() {
		console.log("render the view to display customer search page");
		$(this.el).html(this.template());
		return this;
	},

	events: {
		"keyup #search-text": "search",    // Search for company name in database with specified input - Autocomplete function
		"click #autosuggest-submit": "navigateToDashboard", // Navigate to dashboard to display customer info - Search Button
		//"click #back" : "navigateToHomePage" // Goes to the homepage
	},

	// called when there is a change in the input text
	search: function(event)
	{
		inputString = $('#search-text').val();
		console.log("keyboard entry " + inputString);

		if(inputString.length < 1)
			return;
		if(inputString.length > 0) 
		{
			Customer360App.app.searchcustomer(inputString);
		} 

	},

	// called when the search button is clicked
	navigateToDashboard: function()
	{
		console.log("navigateToDashboard function called");
		inputString = $('#search-text').val();
		if(inputString.length < 1)
		{
			alert("Please select the Customer");
		}
		else
		{
			// || inputString != customerInfo[0]
			if (customerInfo === undefined || inputString != customerInfo[0])
			{
				alert("Result Not Found - Try Again");
			}

			else
			{
				Customer360App.app.navigate('customer/:' + customerInfo[1] + '/name/:' + customerInfo[2], true);
			}
		}

	},

	// Function fetches the values from the database on the basis of input string
	autosuggestion: function(parseData)
	{
		console.log("autosuggestion function of search customer " +parseData);
		// customerId is a global object which holds the customerId of a particular Customer
		customerInfo = parseData;
		$("#search-text").autocomplete({
			source: parseData
		});
	},


});

//DASH BOARD VIEW

Customer360App.views.Dashboard = Backbone.View.extend({

	initialize: function() {
		this.template = Customer360App.utils.templateLoader.get('dashboard-template');
	},

	render: function() {
		console.log("render the view to display customer information");		

		$(this.el).html(this.template(this.model));
		// Jquery accordian for TABBING
		$(this.el).find("#tabs").tabs();
		// Selector - arrangement of Yahoo API Data
		$(this.el).find( "#selectable" ).selectable();
		// Jquery cases BOX
		$(this.el).find( "#selectable-boxes" ).selectable();
		// Jquery scroll bar
		$(this.el).find("#contact-list").slimScroll({height: 600});
		// Tool Tip
		$(document).tooltip();

		return this;
	},	

	events: {
		"click #back" : "navigateToSearchPage",
		"click #cases-btn" : "navigateToCases",
		"click #contracts-btn": "navigateToContracts",
		"click #installbase-btn": "navigateToInstallbase",
		"click #site-btn": "navigateToSite",
	},

	// called when back button is clicked
	navigateToSearchPage: function() {
		console.log("navigateToSearchPage function called");
		Customer360App.app.navigate('', true);
	},
	
	// Navigate to cases detailed info
	navigateToCases: function() {
		console.log("navigateToCases function called");
		Customer360App.app.navigate('cases', true);
	},
	// Navigate to contracts detailed info
	navigateToContracts: function() {
		Customer360App.app.navigate('contracts', true);
	},
	// Navigate to install base detailed info
	navigateToInstallbase: function() {
		Customer360App.app.navigate('installations', true);
	},
	// Navigate to install base detailed info
	navigateToSite: function() {
		Customer360App.app.navigate('locations/:' + customerInfo[1], true);
	},
	
});

// Cases View

Customer360App.views.CasesView = Backbone.View.extend({
	
	initialize: function() {
		this.template = Customer360App.utils.templateLoader.get('cases-template');
	},

	render: function() {
		console.log("render the view to display cases page");
		$(this.el).html(this.template(this.model));
		
		// Jquery scroll bar
		$(this.el).find("#cases-table").slimScroll({height: 700});
		
		// Filter Table
		$(this.el).find("#table").filterTable();
		
		return this;
	},
	events: {
		"click #back" : "navigateToDashboardPage",
	},
	navigateToDashboardPage: function() {
		console.log("navigateToDashboardPage function called");
		Customer360App.app.navigate('customer/:' + customerInfo[1] + '/name/:' + customerInfo[2], true);
	},
});

//Contracts View

Customer360App.views.ContractsView = Backbone.View.extend({
	
	initialize: function() {
		this.template = Customer360App.utils.templateLoader.get('contracts-template');
	},

	render: function() {
		console.log("render the view to display contracts page");
		$(this.el).html(this.template(this.model));
		
		// Jquery scroll bar
		$(this.el).find("#contracts-table").slimScroll({height: 700});
		
		// Filter Table
		$(this.el).find("#table").filterTable();
		
		return this;
	},
	events: {
		"click #back" : "navigateToDashboardPage",
	},
	navigateToDashboardPage: function() {
		console.log("navigateToDashboardPage function called");
		Customer360App.app.navigate('customer/:' + customerInfo[1] + '/name/:' + customerInfo[2], true);
	},
});

//Installbase View

Customer360App.views.InstallbaseView = Backbone.View.extend({
	
	initialize: function() {
		this.template = Customer360App.utils.templateLoader.get('installbase-template');
	},

	render: function() {
		console.log("render the view to display install base page");
		$(this.el).html(this.template(this.model));
		
		// Jquery scroll bar
		$(this.el).find("#installbase-table").slimScroll({height: 700});
		
		// Filter Table
		$(this.el).find("#table").filterTable();
		
		return this;
	},
	events: {
		"click #back" : "navigateToDashboardPage",
	},
	navigateToDashboardPage: function() {
		console.log("navigateToDashboardPage function called");
		Customer360App.app.navigate('customer/:' + customerInfo[1] + '/name/:' + customerInfo[2], true);
	},
});

// Site View

Customer360App.views.SiteView = Backbone.View.extend({
	tagName : 'a',
	initialize: function() {
		this.template = Customer360App.utils.templateLoader.get('sites-template');
	},
	
    
	render: function() {
		console.log("render the view to display site page");
		$(this.el).html(this.template(this.model));
		
		// Tool Tip
		$(document).tooltip();
		
		// Jquery scroll bar
		$(this.el).find("#sites-table").slimScroll({height: 700});
		
		// Filter Table
		$(this.el).find("#table").filterTable();    
		
		return this;
	},
	
	events: {
		"click #back" : "navigateToDashboardPage",
		"click #find" : "displayMap",
		"keyup #geocomplete": "geoSearch",
		"click #site-address-click": "siteAddress"
	},
	
	// To autofill the 
	siteAddress: function() {
		console.log("site address function in site view");
	},
	
	// geo autocomplete
	geoSearch: function() {
		$(this.el).find("#geocomplete").geocomplete({
	          map: ".map_canvas",
	          details: "form ul",
	          detailsAttribute: "data-geo"
	    });
	},
	
	// Displaying Google Maps with address
	displayMap: function() {
		$(this.el).find("#geocomplete").trigger("geocode");
	},
	
	navigateToDashboardPage: function() {
		console.log("navigateToDashboardPage function called");
		Customer360App.app.navigate('customer/:' + customerInfo[1] + '/name/:' + customerInfo[2], true);
	},
});

//----------------------------------------------- The Application Router ------------------------------------------ //

Customer360App.Router = Backbone.Router.extend({

	routes: {

		"": "searchpage",
		"customer/:univId/name/:universityName": "dashboard",
		"cases": "casesInfo",
		"contracts": "contractInfo",
		"installations": "installbaseInfo",
		"locations/:univId": "siteInfo",
	},


	initialize: function() {
		console.log("router initialize");

		var self = this;

		// Keep track of the history of pages (we only store the page URL). Used to identify the direction
		// (left or right) of the sliding transition between pages.

		this.pageHistory = [];

		// Register event listener for back button throughout the app

		$('#content').on('click', '.back-button', function(event) {
			window.history.back();
			return false;
		});

		this.fetchCustomerListURL = "customers.htm";
		this.fetchUserDetailsURL = "userdetails.htm";
		this.fetchCustomerInfoURL_1 = "customers" + "/";
		this.fetchCustomerInfoURL_2 = "name" + "/";
		this.fetchCustomerSearchURL = "search.htm";
		this.fetchCustomerSiteURL = "site" + "/";

	},

	navigate: function(fragment) {
		console.log("navigation router called ");
		Backbone.Router.prototype.navigate.apply(this, arguments);
	},

	/* Search Page Functions - searchpage & searchcustomer. 
 	1. searchpage - loads initial page. 
 	2. searchcustomer - calls autosuggestion functionality */

//	To load initial view of search page. 
	searchpage: function()
	{
		$("#content").empty();
		console.log("search page");
		var CUSTOMERLIST = [];
		Customer360App.app.searchCustomerView = new Customer360App.views.SearchCustomer({model: CUSTOMERLIST});
		Customer360App.app.addPage(Customer360App.app.searchCustomerView.render());
		$("#def_header").hide();
	},

//	Search customer for auto suggestion //

	searchcustomer: function(inputString)
	{
		console.log("Search Customer for auto suggestion" + inputString);
		this.fetchCustomerSearch(inputString);
	},

	//	Function is calling the fetchCustomerInfo

	dashboard: function(customerId, name)
	{
		var id = customerId.substr(1);
		var shortName = name.substr(1);
		console.log("Dashboard "+id + " name "+ shortName);
		$("#content").empty();
		this.fetchCustomerInfo(id, shortName);
	},

	// case info details 
	casesInfo: function() 
	{
		console.log("Case Info Function ");
		console.log(customerJSON);
		$("#content").empty();
		Customer360App.app.casesView = new Customer360App.views.CasesView({model: customerJSON});
		Customer360App.app.addPage(Customer360App.app.casesView.render());
	},
	
	// Contacts Info Details
	contractInfo: function()
	{
		console.log("Contract Info Function");
		console.log(customerJSON);
		$("#content").empty();
		Customer360App.app.contractView = new Customer360App.views.ContractsView({model: customerJSON});
		Customer360App.app.addPage(Customer360App.app.contractView.render());
	},
	
	// Install Base Info Details
	installbaseInfo: function()
	{
		console.log("Install Base Info Function");
		console.log(customerJSON);
		$("#content").empty();
		Customer360App.app.installbaseView = new Customer360App.views.InstallbaseView({model: customerJSON});
		Customer360App.app.addPage(Customer360App.app.installbaseView.render());
	},
	
	// Site Info Details
	siteInfo: function(customerId)
	{
		var id = customerId.substr(1);
		console.log("Site Info Function");
		console.log(customerJSON);
		$("#content").empty();
		this.fetchSiteDetails(id);
//		Customer360App.app.siteView = new Customer360App.views.SiteView({model: customerJSON});
//		Customer360App.app.addPage(Customer360App.app.siteView.render());
	},
	
	/* INTI VIEW */

//	Function to call the Customer List View      (Need to implement the event for search option)

	initCustomerListView: function(parseData){
		console.log("init view for customer list - parsed Data");
		//console.log(parseData);
		Customer360App.app.transactions.set(parseData);
		Customer360App.app.customerListView = new Customer360App.views.CustomerList({model: parseData});
		Customer360App.app.addPage(Customer360App.app.customerListView.render());
		$("#def_header").hide();
	},


	initCustomerSearchView: function(parseData){
		console.log("init view for customer search -  parsed Data");
		//console.log(parseData);
		Customer360App.app.transactions.set(parseData);

		Customer360App.app.searchCustomerResultView = new Customer360App.views.SearchCustomer({model: parseData});

		// calling auto suggestion
		Customer360App.app.searchCustomerResultView.autosuggestion(parseData);

		//Customer360App.app.addPage(Customer360App.app.searchCustomerResultView.render());
		$("#def_header").hide();
	},

//	Function to call the Customer Information View

	initDashboardView: function(parseData){
		console.log("init view for customer information DASHBOARD VIEW - parsed Data");
		//console.log(parseData);
		// storing the json in a Global variable
		customerJSON = parseData;
		console.log(customerJSON)
		Customer360App.app.transactions.set(parseData);
		Customer360App.app.dashboardView = new Customer360App.views.Dashboard({model: parseData});

		Customer360App.app.addPage(Customer360App.app.dashboardView.render());
		$("#def_header").hide();

	},
	
//	Function to call the Customer Site View
	
	initCustomerSiteView: function(parseData) {
		console.log("init view for site information - parsed Data");
		Customer360App.app.transactions.set(parseData);
		Customer360App.app.siteView = new Customer360App.views.SiteView({model: parseData});
		
		Customer360App.app.addPage(Customer360App.app.siteView.render());
		$("#def_header").hide();
	},

// Site Location Details
	
	fetchSiteDetails: function(customerId){
		console.log("fetch site detials with customerId" +customerId);
		this.transactions = new Customer360App.models.Transition();
		self = this;
		//console.log(this.fetchCustomerSiteURL);
		var URL = this.fetchCustomerSiteURL + customerId + ".htm";
		console.log("URL = "+URL);

		// setting the URL with request parameter //
		this.transactions.setURL(URL);

		this.transactions.fetch({
			dataType: "json",
			success: function(customerSiteData) {
				Customer360App.app.initCustomerSiteView(Customer360App.app.parser("SITEDETAIL",customerSiteData.toJSON()));
			},
			error:function(customerSiteData){
				console.log(customerSiteData);
				console.log("Error occurred while retrieving the customers list");
			}
		});
	},
	
//	Search Customer List of Customer360 App

	fetchCustomerSearch: function(inputString){
		console.log("fetchCusomterSearch with key " +inputString);
		this.transactions = new Customer360App.models.Transition();
		self = this;
		console.log(this.fetchCustomerSearchURL);
		var URL = this.fetchCustomerSearchURL + '?startsWith=' + inputString ;
		console.log("URL = "+URL);

		// setting the URL with request parameter //
		this.transactions.setURL(URL);

		this.transactions.fetch({
			dataType: "json",
			success: function(customerSearchData) {
				//console.log(customerSearchData);
				Customer360App.app.initCustomerSearchView(Customer360App.app.parser("CUSTOMERSEARCH",customerSearchData.toJSON()));
			},
			error:function(customerSearchData){
				console.log(customerSearchData);
				console.log("Error occurred while retrieving the customers list");
			}
		});
	},


//	The function is fetching the customer info from the Spring MVC Restful API  - DashBoard Page Display

	fetchCustomerInfo: function(customerId, name) {
		console.log("fetchCustomerInfo");
		this.transactions = new Customer360App.models.Transition();
		self = this;
		this.fetchCustomerInfoURL_1 = "customers" + "/";
		this.fetchCustomerInfoURL_2 = "name" + "/";
		var URL = this.fetchCustomerInfoURL_1 + customerId + "/" + this.fetchCustomerInfoURL_2 + name + ".htm";
		console.log(URL);
		this.transactions.setURL(URL); 

		this.transactions.fetch({
			dataType: "json",
			success: function(customerInfo) {
				//console.log(customerInfo);
				Customer360App.app.initDashboardView(Customer360App.app.parser("CUSTOMERINFO", customerInfo.toJSON()));
			},
			error:function(customerInfo){
				console.log("Error occured while retriving transactions");
			}
		});
	},


//	Json Parser Function //

	parser:function(route, jsonData){
		console.log("parse function with route " + route);

		// Converting json data into json string format
		var jsonString = JSON.stringify(jsonData);
		console.log("JSON STRING FORMAT");
		console.log(jsonString);

		// json object 
		var jsonObject = JSON.parse(jsonString);

		// Variables Required //

		var myobject = {};
		myobject.CUSTOMERINFO = [];
		myobject.DASHBOARDINFO = [];
		myobject.YAHOOAPIINFO = [];
		myobject.SITEINFO = [];
		var CUSTOMERLIST = [];

		// Dashboard Page Variables //
		var priority1Count = 0;
		var priority2Count = 0;
		var escalationCount = 0;
		var siteCount = 0;
		var installCount = 0;
		var installQuantity = 0;
		var contractCount = 0;


		switch(route)
		{
		case "SITEDETAIL":
		{
			console.log("SITE DETAIL PARSING");
					
				for(var key in jsonObject)
					{
					try
						{
							var siteInfoObject = {};
							siteInfoObject.siteId = jsonObject[key]["siteId"];
							siteInfoObject.siteName = jsonObject[key]["siteName"];
							siteInfoObject.siteAddr = jsonObject[key]["siteAddr"];
							siteInfoObject.siteAddr2 = jsonObject[key]["siteAddr2"];
							siteInfoObject.city = jsonObject[key]["city"];
							siteInfoObject.state = jsonObject[key]["state"];
							siteInfoObject.region = jsonObject[key]["region"];
							siteInfoObject.country = jsonObject[key]["country"];
							siteInfoObject.zip = jsonObject[key]["zip"];
							
							myobject.SITEINFO.push(siteInfoObject);
							console.log(siteInfoObject);
						}
					 catch(err)
					 	{
						 	console.log("Error occurred while parsing the site table details " + jsonObject + "Error Message " + err.message);
					 	}
					 
					}
					break;
		}
		case "CUSTOMERSEARCH":
		{
			console.log("CUSTOMER SEARCH PARSING");
			for (var key in jsonObject)
			{
				try {
					CUSTOMERLIST.push(jsonObject[key]["universityName"]);
					CUSTOMERLIST.push(jsonObject[key]["univId"]);
					//CUSTOMERLIST.push(jsonObject[key]["shortName"]);
//					var customerListObject = {};
//					customerListObject.customerId = jsonObject[key]["customerId"];
//					customerListObject.customerName = jsonObject[key]["customerName"];
//					customerListObject.siteId = jsonObject[key]["siteId"];
					//customerList = jsonObject[key]["customerName"];
					//myobject.CUSTOMERLIST.push(customerListObject);
					
				}
				catch(err)
				{
					console.log("Error occurred while parsing the json string" + jsonObject[key] + "Error Message" + err.message);
				}
			}
			break;
		}
		case "CUSTOMERINFO":
		{
			console.log("CUSTOMER INFO PARSING");
			for (var key in jsonObject)
			{
				//var customerInfoObject = {};
				var keyholdercases = jsonObject[key]["cases"];
				var keyholderinstallbase = jsonObject[key]["installbase"];
				var keyholdercontracts = jsonObject[key]["contract"];
				var keyholdercontacts = jsonObject[key]["contacts"];
				var keyholdercustomers = jsonObject[key]["customers"];
				var keyholdersites = jsonObject[key]["site"];
				
				// If the Yahoo Finance API is down it return NULL or if the user is not connected to Internet - The exception is handled here for NULL condition 
				if(jsonObject[key]["yahooAPI"] === null)
					{
						var yahooAPIObject = {};
						yahooAPIObject.InternetProblem = jsonObject[key]["yahooAPI"] === null;
						myobject.YAHOOAPIINFO.push(yahooAPIObject);
					}
				else if(jsonObject[key]["yahooAPI"]["query"]["results"] === null)
					{
						var yahooAPIObject = {};
						yahooAPIObject.ServerDown = jsonObject[key]["yahooAPI"]["query"]["results"] === null;
						myobject.YAHOOAPIINFO.push(yahooAPIObject);
					}
				else
					{
						var keyholderyahooAPI = jsonObject[key]["yahooAPI"]["query"]["results"]["stats"];
						
						// parsing yahooAPI
						try
						{
							var yahooAPIObject = {};
							yahooAPIObject.symbol = keyholderyahooAPI["symbol"];
							yahooAPIObject.OperatingMargin = keyholderyahooAPI["OperatingMargin"]["content"];
							yahooAPIObject.ProfitMargin = keyholderyahooAPI["ProfitMargin"]["content"];
							yahooAPIObject.EBITDA = keyholderyahooAPI["EBITDA"]["content"];
							yahooAPIObject.ReturnonAssets = keyholderyahooAPI["ReturnonAssets"]["content"];
							yahooAPIObject.ReturnonEquity = keyholderyahooAPI["ReturnonEquity"]["content"];
							yahooAPIObject.Revenue = keyholderyahooAPI["Revenue"]["content"];

							myobject.YAHOOAPIINFO.push(yahooAPIObject);
						}
						catch(err)
						{
							console.log("Error occurred while parsing the Yahoo API" + jsonObject[key] + "Error Message" + err.message);
						}
					}

				// Parsing the cases of customer info //
				try
				{

					for(var i in keyholdercases)
					{
						var customerInfoObject = {};
						var dashboardObject = {};
						customerInfoObject.caseNumber = keyholdercases[i]["caseNumber"];
						customerInfoObject.caseEscalation = keyholdercases[i]["caseEscalation"];
						if(keyholdercases[i]["caseEscalation"] == 1)
						{
							escalationCount++;
						}
						customerInfoObject.caseSynopsis = keyholdercases[i]["caseSynopsis"];
						customerInfoObject.caseCreatedDate = keyholdercases[i]["caseCreatedDate"];
						customerInfoObject.contactName = keyholdercases[i]["contactName"];
						customerInfoObject.contactEmail = keyholdercases[i]["contactEmail"];
						customerInfoObject.priority = keyholdercases[i]["priority"];
						if(keyholdercases[i]["priority"] == 'p1')
						{
							//console.log("priority1Count "+priority1Count);
							priority1Count++;
						}
						else if(keyholdercases[i]["priority"] == 'p2')
						{
							//console.log("priority2Count "+priority2Count);
							priority2Count++;
						}

						myobject.CUSTOMERINFO.push(customerInfoObject);


					}
					dashboardObject.priority1 = priority1Count;
					dashboardObject.priority2 = priority2Count;
					dashboardObject.escalations = escalationCount;

					myobject.DASHBOARDINFO.push(dashboardObject);
					console.log(dashboardObject);

				}
				catch(err)
				{
					console.log("Error occurred while parsing the cases" + jsonObject[key] + "Error Message" + err.message);
				}

				// Parsing the install base of customer info //
				try
				{
					var uniqueSiteArray = [];
					for(var i in keyholderinstallbase)
					{
						var customerInfoObject = {};
						var dashboardObject = {};

						customerInfoObject.serialNumber = keyholderinstallbase[i]["serialNumber"];
						customerInfoObject.serialNumberCount = keyholderinstallbase[i]["serialNumberCount"];
						if(keyholderinstallbase[i]["serialNumberCount"] == 1)
						{
							installCount++;
						}
						customerInfoObject.instanceName = keyholderinstallbase[i]["instanceName"];
						customerInfoObject.installPartId = keyholderinstallbase[i]["installPartId"];
						customerInfoObject.installQuantity = keyholderinstallbase[i]["installQuantity"];

						installQuantity = installQuantity + parseInt(keyholderinstallbase[i]["installQuantity"]);

						customerInfoObject.installQuarter = keyholderinstallbase[i]["installQuarter"];
						customerInfoObject.installDate = keyholderinstallbase[i]["installDate"];
						customerInfoObject.siteId = keyholderinstallbase[i]["siteId"];
						var indexVal = uniqueSiteArray.indexOf(keyholderinstallbase[i]["siteId"]);
						if (indexVal === -1)
						{
							//console.log("value "+keyholderinstallbase[i]["siteId"]+ "array length "+uniqueSiteArray.length);
							uniqueSiteArray.push(keyholderinstallbase[i]["siteId"]);
						}

						myobject.CUSTOMERINFO.push(customerInfoObject);
						//console.log(customerInfoObject);
					}
					dashboardObject.installCount = installCount;
					dashboardObject.installLocations = uniqueSiteArray.length;
					dashboardObject.installQuantity = installQuantity;
					myobject.DASHBOARDINFO.push(dashboardObject);
					console.log(dashboardObject);
				}
				catch(err)
				{
					console.log("Error occurred while parsing the isntall base " + jsonObject[key] + "Error Message" + err.message);
				}


				// Parsing the contracts of customer info //
				try
				{
					var uniqueContrctIdsArray = [];
					var uniqueContractBillIdsArray = [];
					for(var i in keyholdercontracts)
					{
						var customerInfoObject = {};
						var dashboardObject = {};
						customerInfoObject.contractId = keyholdercontracts[i]["contractId"];
						var indexVal_1 = uniqueContrctIdsArray.indexOf(keyholdercontracts[i]["contractId"])
						if (indexVal_1 === -1)
						{
							//console.log("value "+keyholderinstallbase[i]["siteId"]+ "array length "+uniqueSiteArray.length);
							uniqueContrctIdsArray.push(keyholdercontracts[i]["contractId"]);
						}
						customerInfoObject.contractDesc = keyholdercontracts[i]["contractDesc"];
						customerInfoObject.contractSerialNumber = keyholdercontracts[i]["contractSerialNumber"];

						contractCount++;

						customerInfoObject.contractBillToName = keyholdercontracts[i]["contractBillToName"];
						customerInfoObject.contractBillToId = keyholdercontracts[i]["contractBillToId"];

						var indexVal_2 = uniqueContractBillIdsArray.indexOf(keyholdercontracts[i]["contractBillToId"])
						if (indexVal_2 === -1)
						{
							//console.log("value "+keyholderinstallbase[i]["siteId"]+ "array length "+uniqueSiteArray.length);
							uniqueContractBillIdsArray.push(keyholdercontracts[i]["contractBillToId"]);
						}

						customerInfoObject.contractBillAddr = keyholdercontracts[i]["contractBillAddr"];
						customerInfoObject.contractBillAddr2 = keyholdercontracts[i]["contractBillAddr2"];
						customerInfoObject.contractBillCity = keyholdercontracts[i]["contractBillCity"];
						customerInfoObject.contractBillState = keyholdercontracts[i]["contractBillState"];
						customerInfoObject.contractBillRegion = keyholdercontracts[i]["contractBillRegion"];
						customerInfoObject.contractBillCountry = keyholdercontracts[i]["contractBillCountry"];
						customerInfoObject.contractBillZip = keyholdercontracts[i]["contractBillZip"];
						customerInfoObject.contractStartDate = keyholdercontracts[i]["contractStartDate"];
						myobject.CUSTOMERINFO.push(customerInfoObject);
						//console.log(customerInfoObject);
					}
					dashboardObject.contractIdCount = uniqueContrctIdsArray.length;
					dashboardObject.contractCount = contractCount;
					dashboardObject.contractLocation = uniqueContractBillIdsArray.length;
					myobject.DASHBOARDINFO.push(dashboardObject);
					console.log(dashboardObject);
				}
				catch(err)
				{
					console.log("Error occurred while parsing the contracts " + jsonObject[key] + "Error Message" + err.message);
				}

				// Parsing the contacts //
				try
				{
					for(var i in keyholdercontacts)
					{
						var customerInfoObject = {};
						customerInfoObject.name = keyholdercontacts[i]["name"];
						customerInfoObject.email = keyholdercontacts[i]["email"];
						customerInfoObject.image = keyholdercontacts[i]["image"];
						myobject.CUSTOMERINFO.push(customerInfoObject);
						//console.log(customerInfoObject);
					}
				}
				catch(err)
				{
					console.log("Error occurred while parsing the contacts " + jsonObject[key] + "Error Message" + err.message);
				}

				// Parsing the customer - Contains data - [customerId, customername, siteId, shortName]
				// Parse all the site Ids of the table and takes the unique values of customerName, customerId, shortName
				try
				{
					for(var i in keyholdercustomers)
					{
						var customerInfoObject = {};
						var dashboardObject = {};
						customerInfoObject.siteId = keyholdercustomers[i]["siteId"];
						customerInfoObject.shortName = keyholdercustomers[i]["shortName"];
						if(keyholdercustomers[i]["siteId"] != null)
						{
							siteCount++;
						}
						if(i == 0)
						{
							console.log("i = "+i);
							customerInfoObject.customerId = keyholdercustomers[i]["univId"];
							customerInfoObject.customerName = keyholdercustomers[i]["universityName"];

							
							//console.log(dashboardObject);
							myobject.CUSTOMERINFO.push(customerInfoObject);
						}
						else
						{
							myobject.CUSTOMERINFO.push(customerInfoObject);
						}

						//console.log(customerInfoObject);
					}
					
					dashboardObject.customerId = keyholdercustomers[0]["univId"];
					dashboardObject.customerName = keyholdercustomers[0]["universityName"];
					dashboardObject.siteCount = siteCount;
					myobject.DASHBOARDINFO.push(dashboardObject);
					console.log(dashboardObject);
				}
				catch(err)
				{
					console.log("Error occurred while parsing the customer table details " + jsonObject[key] + "Error Message" + err.message);
				}

				// parsing the site table of the customer ------- NOT USED
				/*try
				{

					for(var i in keyholdersites)
					{
						var customerInfoObject = {};
						customerInfoObject.siteId = keyholdersites[i]["siteId"];
						myobject.CUSTOMERINFO.push(customerInfoObject);
					}
				}
				catch(err)
				{
					console.log("Error occurred while parsing the site table details " + jsonObject[key] + "Error Message" + err.message);
				}*/
			}
			break;
		}

		}
		if (route == "CUSTOMERSEARCH")
		{
			// Returns the list of customers list with specified user input. 
			return CUSTOMERLIST;
		}
		else
		{
			return myobject;
		}

	},

	addPage: function(page) {
		console.log("addpage");
		var slideFrom,
		self = this;
		$('#content').append(page.el);
	},

});


Customer360App.utils.templateLoader.load(['index-page-template', 'search-page-template', 'dashboard-template' , 'cases-template' , 'installbase-template' , 'contracts-template' , 'sites-template'],
		function() {
	//console.log("Customer360App.utils.templateLoader.load");
	Customer360App.app = new Customer360App.Router();
	Backbone.history.start();
});



