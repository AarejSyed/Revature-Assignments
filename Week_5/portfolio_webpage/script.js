/*
    JavaScript API Call Challenge: Add an API call to a webpage via JavaScript
    
    This script populates the section "Near-Earth Objects Making Their Closest Approach to Earth Today" with a list of entries.
    Entries are from the Near Earth Object Web Service API.
*/

"use strict";

// Fetches from Near Earth Object Web Service's Today API and parses response
const fetchAndParseFromNeowsToday = async () => {
    const response = await fetch("https://neowsapp.com/rest/v1/feed/today");
    return await response.json();
}

// Gets list of today's NEOs from parsed NeoWs response
const getTodaysNeos = (parsedNeowsResponse) => {
    return Object.values(parsedNeowsResponse.near_earth_objects)[0]
}

// Creates general entry element
const createEntryElement = (elementType, id) => {
    const entryElement = document.createElement(elementType);
    entryElement.classList.add("entry");
    entryElement.id = id;
    return entryElement;
}

// Creates NEO datum HTML element
const createNeoDatumHtmlElement = (heading, data, id) => {
    const neoDatumHtmlElement = document.createElement("p");
    neoDatumHtmlElement.innerHTML = `<strong>${heading}:</strong> ${data}`;
    return neoDatumHtmlElement;
}

// Creates NEO HTML element
const createNeoHtmlElement = (neo) => {
    // NEO data (for easy access)
    const closeApproachData = neo.close_approach_data[0];
    const missDistance = closeApproachData.miss_distance;
    const estimatedDiameter = neo.estimated_diameter;
    
    // Create empty NEO HTML element
    const neoHtmlElement = createEntryElement("section", "neo-" + neo.id);

    // Element heading
    const neoHtmlElementHeading = document.createElement("h3");
    neoHtmlElementHeading.textContent = neo.name;
    neoHtmlElement.appendChild(neoHtmlElementHeading);

    // Date/time of closest approach
    neoHtmlElement.appendChild(createNeoDatumHtmlElement(
        "Time of closest approach",
        new Date(Number(closeApproachData.epoch_date_close_approach)).toLocaleTimeString()
    ))

    // Distance of closest approach
    neoHtmlElement.appendChild(createNeoDatumHtmlElement(
        "Distance of closest approach",
        `${Math.round(Number(missDistance.kilometers))} kilometers / ${Math.round(Number(missDistance.miles))} miles`
    ))

    // Estimated minimum and maximum diameters
    neoHtmlElement.appendChild(createNeoDatumHtmlElement(
        "Estimated minimum diameter",
        `${Math.round(Number(estimatedDiameter.meters.estimated_diameter_min))} meters`
    ))
    neoHtmlElement.appendChild(createNeoDatumHtmlElement(
        "Estimated maximum diameter",
        `${Math.round(Number(estimatedDiameter.meters.estimated_diameter_max))} meters`
    ))

    // Potentially hazardous asteroid indicator
    const phaIndicator = document.createElement("p");
    phaIndicator.innerHTML = (neo.is_potentially_hazardous_asteroid) ?
        "<strong>Potentially hazardous asteroid!</strong>" :
        "<strong>Not a potentially hazardous asteroid.</strong>";
    phaIndicator.style.color = (neo.is_potentially_hazardous_asteroid) ? "red" : "green";
    neoHtmlElement.appendChild(phaIndicator);

    // Link to entry in JPL Small-Body Database
    const smallBodyDatabaseLink = document.createElement("p");
    smallBodyDatabaseLink.innerHTML = `<a href="${neo.nasa_jpl_url}">Entry in JPL Small-Body Database</a>`
    neoHtmlElement.appendChild(smallBodyDatabaseLink);

    return neoHtmlElement;
}

// Creates no-neos-for-today element
const createNoNeosForTodayElement = () => {
    const noNeosForTodayElement = createEntryElement("p", "no-neos-for-today");
    noNeosForTodayElement.textContent = "No NEOs are making their closest approach to Earth today. Check again tomorrow!";
    return noNeosForTodayElement;
}

// Main method wrapping all logic in async block
const main = async () => {
    // Get relevant HTML elements
    const neosListElement = document.getElementById("todays-neos-list");
    const loadingIndicatorElement = document.getElementById("todays-neos-list-loading-indicator");

    // Get parsed NeoWs response
    const parsedNeowsResponse = await fetchAndParseFromNeowsToday();
    
    // Remove loading indicator
    neosListElement.removeChild(loadingIndicatorElement);

    // If there are no NEOs to add, we add element saying this instead of generating NEO HTML elements
    if (parsedNeowsResponse.element_count == 0) {
        neosListElement.appendChild(createNoNeosForTodayElement());
    }

    // If there are NEOs to add, we generate and add elements for each NEO
    else {
        // Get list of today's NEOs from parsed NeoWs response
        const todaysNeos = getTodaysNeos(parsedNeowsResponse);

        todaysNeos.forEach((neo) => {
            neosListElement.appendChild(createNeoHtmlElement(neo));
        })
    }
}

main();