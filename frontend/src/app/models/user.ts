import {Authentication} from "./authentication";

export class User {
  id: number;
  username: string = "frontend";
  password: string = "12345";
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
