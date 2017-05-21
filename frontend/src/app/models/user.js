"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var WebUser = (function () {
    function WebUser() {
        this.email = "frontend@capjavert.dev";
        this.password = "12345";
    }
    WebUser.prototype.authenticate = function (auth) {
        this.token = auth.token;
    };
    return WebUser;
}());
exports.WebUser = WebUser;
//# sourceMappingURL=user.js.map