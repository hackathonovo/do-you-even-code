"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var http_1 = require("@angular/http");
var Observable_1 = require("rxjs/Observable");
var session_1 = require("../session");
var PointService = PointService_1 = (function () {
    function PointService(http) {
        this.http = http;
        this.baseUrl = 'http://46.101.106.208:3000'; // URL to web API
        this.headers = new http_1.Headers({ 'Content-Type': 'application/x-www-form-urlencoded', 'Authorization': session_1.user.token });
        if (!session_1.user.token) {
            this.handshake()
                .subscribe(function (auth) {
                session_1.user.authenticate(auth);
            });
        }
    }
    PointService.prototype.handshake = function () {
        var url = this.baseUrl + '/login';
        return this.http
            .post(url, JSON.stringify({ "email": session_1.user.email, "password": session_1.user.password }), { headers: this.headers })
            .map(PointService_1.extractData)
            .catch(PointService_1.handleError);
    };
    PointService.prototype.list = function () {
        var params = new http_1.URLSearchParams();
        params.set('format', 'json');
        params.set('callback', 'JSONP_CALLBACK');
        return this.http.get(this.baseUrl + '/points', params)
            .map(PointService_1.extractData)
            .catch(PointService_1.handleError);
    };
    PointService.prototype.get = function (id) {
        var params = new http_1.URLSearchParams();
        params.set('format', 'json');
        params.set('callback', 'JSONP_CALLBACK');
        return this.http.get(this.baseUrl + '/points/' + id, params)
            .map(PointService_1.extractData)
            .catch(PointService_1.handleError);
    };
    PointService.prototype.create = function (model) {
        var url = this.baseUrl + '/points';
        return this.http
            .post(url, JSON.stringify(model), { headers: this.headers })
            .toPromise()
            .catch(PointService_1.handleError);
    };
    PointService.prototype.remove = function (id) {
        var url = this.baseUrl + '/points/' + id;
        return this.http
            .delete(url, { headers: this.headers })
            .toPromise()
            .catch(PointService_1.handleError);
    };
    PointService.prototype.update = function (model) {
        var url = this.baseUrl + '/points/' + model.id;
        return this.http
            .put(url, JSON.stringify(model), { headers: this.headers })
            .toPromise()
            .then(function () { return model; })
            .catch(PointService_1.handleError);
    };
    PointService.extractData = function (res) {
        var body = res.json();
        return body || {};
    };
    PointService.handleError = function (error) {
        var errMsg;
        if (error instanceof http_1.Response) {
            var body = error.json() || '';
            var err = body.error || JSON.stringify(body);
            errMsg = error.status + " - " + (error.statusText || '') + " " + err;
        }
        else {
            errMsg = error.message ? error.message : error.toString();
        }
        console.error(errMsg);
        return Observable_1.Observable.throw(errMsg);
    };
    return PointService;
}());
PointService = PointService_1 = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [http_1.Http])
], PointService);
exports.PointService = PointService;
var PointService_1;
//# sourceMappingURL=point.service.js.map