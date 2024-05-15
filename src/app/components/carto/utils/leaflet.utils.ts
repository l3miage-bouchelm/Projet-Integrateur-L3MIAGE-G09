<<<<<<< HEAD
import { Map as LeafletMap, LatLng } from "leaflet";

export function recenter({ leafletMap, latlng }: { leafletMap: LeafletMap, latlng: LatLng }) {
    // leafletMap.dragging.disable()
    leafletMap.invalidateSize({ animate: false })
    leafletMap.setView(latlng, leafletMap.getZoom() ?? 15);
=======
import { Map as LeafletMap, LatLng } from "leaflet";

export function recenter({ leafletMap, latlng }: { leafletMap: LeafletMap, latlng: LatLng }) {
    // leafletMap.dragging.disable()
    leafletMap.invalidateSize({ animate: false })
    leafletMap.setView(latlng, leafletMap.getZoom() ?? 15);
>>>>>>> 4d93b5eaad341077ba9d9163750d2e71240d4191
}