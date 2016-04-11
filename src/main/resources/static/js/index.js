$(document)
  .ready(
    function() {

	    if (window.location.protocol === "https:") {
		    window.location.protocol = "http";
	    }

	    // map
	    var geoJson;
	    var map;

	    // ssb
	    var currentPropIndex = 0;
	    var ssbData;

	    $.getJSON("/ssbdata", function(data) {
		    ssbData = data;
		    setDsInfo();
		    setVariableInfo();
		    setVariablesRadio();

		    // event handlers
		    $("#variablesRadio").on("change", function() {
			    currentPropIndex = $(this).find(':checked').val();
			    setVariableInfo();
			    updateMap();
		    });

		    // first display
		    displayMap();
		    updateMap();
		    $('input:radio[value=0]').attr('checked', true);
	    });

	    function setDsInfo() {
		    $("#dsLastQuarter").text(ssbData.lastQuarter);
		    $("#dsSource").text(ssbData.datasetSource);
		    $("#dsUpdated").text(new Date(ssbData.datasetUpdated).toDateString());
		    $("#dsInfo").show();
	    }

	    function setVariableInfo() {
		    $("#variableName").text(ssbData.variablesNames[currentPropIndex]);
		    $("#variableInfo").show();
	    }

	    function updateVariableInfo(feature) {
		    if (feature) {
			    $("#variableInfo #countyName").text(feature.properties.NAVN);
			    var value = ssbData.variablesValues[feature.id][currentPropIndex];
			    value = value.toLocaleString();
			    $("#variableInfo #variableValue").text(value);
		    } else {
			    $("#variableInfo #countyName").text("");
			    $("#variableInfo #variableValue").text("");
		    }
	    }

	    function setVariablesRadio() {
		    var $radio = $("#variablesRadio");
		    ssbData.variablesNames
		      .forEach(function name(element, index) {
			      $radio
			        .append("<div class='radio'><label> <input name='variables' type='radio' value="
			          + index + ">" + element + "</label></div>");
		      });
	    }

	    // map events
	    function onEachFeature(feature, layer) {
		    layer.on({
		     mouseover : highlightFeature,
		     mouseout : resetHighlight
		    });
	    }

	    function highlightFeature(e) {
		    var layer = e.target;

		    layer.setStyle({
		     weight : 1,
		     color : 'white',
		     dashArray : '',
		     fillOpacity : .3
		    });

		    if (!L.Browser.ie && !L.Browser.opera) {
			    layer.bringToFront();
		    }

		    updateVariableInfo(layer.feature);
	    }

	    function resetHighlight(e) {
		    geoJson.resetStyle(e.target);
		    updateVariableInfo();
	    }
	    // end map events

	    // map display and update
	    function updateMap() {
		    if (geoJson) {
			    map.removeLayer(geoJson);
		    }

		    geoJson = L.geoJson(counties, {
		     style : style,
		     onEachFeature : onEachFeature
		    }).addTo(map);

	    }

	    function displayMap() {
		    map = L.map('chloro', {
		     zoomControl : false,
		     dragging : false,
		     touchZoom : false,
		     scrollWheelZoom : false,
		     doubleClickZoom : false,
		     boxZoom : false,
		     attributionControl : false
		    }).setView([ 65.2, 18 ], 5.3);

	    }
	    // end map display and update

	    // colors
	    function getColor(id) {

		    var colorCode = ssbData.colorCodes[currentPropIndex];
		    var colors = colorbrewer[colorCode]['6'];
		    var value = ssbData.variablesValues[id][currentPropIndex];

		    if (value <= ssbData.percentiles[currentPropIndex][0]) {
			    return colors[1];
		    }
		    if (value <= ssbData.percentiles[currentPropIndex][1]) {
			    return colors[2];
		    }
		    if (value <= ssbData.percentiles[currentPropIndex][2]) {
			    return colors[3];
		    }
		    if (value <= ssbData.percentiles[currentPropIndex][3]) {
			    return colors[4];
		    }
		    return colors[5];
	    }

	    function style(feature) {
		    return {
		     fillColor : getColor(feature.id),
		     weight : 1,
		     opacity : 1,
		     color : 'white',
		     dashArray : '1',
		     fillOpacity : 1
		    };
	    }

    });
