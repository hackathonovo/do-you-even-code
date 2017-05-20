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
var router_1 = require("@angular/router");
var common_1 = require("@angular/common");
require("../../rxjs-operators");
var point_service_1 = require("../../services/point.service.ts");
var point_1 = require("../../models/point");
var PointDetailComponent = (function () {
    function PointDetailComponent(pointService, route, location) {
        this.pointService = pointService;
        this.route = route;
        this.location = location;
    }
    PointDetailComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params
            .switchMap(function (params) { return _this.pointService.get(+params['id']); })
            .subscribe(function (point) { return _this.model = point; });
    };
    PointDetailComponent.prototype.save = function () {
        var _this = this;
        this.pointService.update(this.model)
            .then(function () { return _this.goBack(); });
    };
    PointDetailComponent.prototype.goBack = function () {
        this.location.back();
    };
    return PointDetailComponent;
}());
__decorate([
    core_1.Input(),
    __metadata("design:type", point_1.Point)
], PointDetailComponent.prototype, "model", void 0);
PointDetailComponent = __decorate([
    core_1.Component({
        moduleId: module.id,
        selector: 'my-point-detail',
        templateUrl: 'point-detail.component.html',
        styleUrls: ['point-detail.component.css']
    }),
    __metadata("design:paramtypes", [point_service_1.PointService,
        router_1.ActivatedRoute,
        common_1.Location])
], PointDetailComponent);
exports.PointDetailComponent = PointDetailComponent;
//# sourceMappingURL=point-detail-component.js.map
