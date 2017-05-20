import {LatLngLiteral} from "@agm/core";

export class Polygon {
  id: number = null;
  type: string;
  data: string;
  polygon: Array<LatLngLiteral> = [];
  color: string = "green";
}
