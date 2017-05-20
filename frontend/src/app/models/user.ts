import {Authentication} from "./authentication";

export class User {
  id: number;
  username: string;
  password: string;
  name: string;
  surname: string;
  role: string;
  data: string;
  address: string;
  token: string;
  phone: string;
  action_id: number;

  public authenticate(auth: Authentication) {
    this.token = auth.token;
  }
}
