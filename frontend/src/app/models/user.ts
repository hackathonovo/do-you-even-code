import {Authentication} from "./authentication";

export class User {
  id: number;
  username: string = "voditelj";
  password: string = "1234";
  name: string;
  surname: string;
  role: string;
  data: string;
  address: string;
  token: string;

  public authenticate(auth: Authentication) {
    this.token = auth.token;
  }
}
