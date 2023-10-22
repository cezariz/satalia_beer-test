function createPopUp(waypoint, index) {
  if (index == 0) return;
  if (!waypoint.brewery) {
    return `<strong>0 - Home</strong><br /> Total Trip Distance: <span style="color: #007BFF;">${waypoint.distanceTraveled} km</span>`;
  }

  // Filter out beers with blank or just whitespace names
  const beersList = waypoint.brewery.beers
    .map(beer => beer.name.trim())
    .filter(name => name !== "");

  const beers = !waypoint.brewery || !beersList.length ?
  'Brewery does not have any beers' :
  `
    <strong>Beers:</strong>
    <table style="margin-top: 5px; width: 100%; border-collapse: collapse;">
        <tbody>
            ${beersList.map((beer, i) => `
                <tr>
                    <td style="border: 1px solid #007BFF; padding: 5px;">${i + 1}</td>
                    <td style="border: 1px solid #007BFF; padding: 5px;">${beer}</td>
                </tr>
            `).join('')}
        </tbody>
    </table>
  `;

  var popUp = `
    <div style="max-height: 300px; overflow: scroll; font-family: Arial, sans-serif; padding: 8px;">
        <strong>${index} - ${waypoint.brewery.name}</strong>
        <br />
        Distance traveled: <span style="color: #007BFF;">${waypoint.distanceTraveled} km</span>
        <br />
        Distance to previous waypoint: <span style="color: #007BFF;">${waypoint.distanceToPreviousPoint} km</span>
        <br /><br />
        ${beers}
    </div>
  `;

  return popUp;
}

const homeIcon = new L.Icon({
  iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-blue.png',
  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowSize: [41, 41]
});

const breweryIcon = new L.Icon({
  iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-red.png',
  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowSize: [41, 41]
});

const map = L.map('map').setView([51.505, -0.09], 13);

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
  attribution: '© OpenStreetMap contributors'
}).addTo(map);

const latlngs = [];

for (var idx = 0; idx < waypoints.length; idx++) {
  if (waypoints[idx].lat && waypoints[idx].lon) {
    latlngs.push([waypoints[idx].lat, waypoints[idx].lon]);

    const currentIcon = (idx == 0 || idx == waypoints.length - 1) ? homeIcon : breweryIcon;

    L.marker([waypoints[idx].lat, waypoints[idx].lon], { icon: currentIcon, title: idx })
      .addTo(map)
      .bindPopup(createPopUp(waypoints[idx], idx));
  }
}

const polyline = L.polyline(latlngs, {color: 'green'}).addTo(map);

map.fitBounds(polyline.getBounds());
