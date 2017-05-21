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
var common_1 = require("@angular/common");
require("../../rxjs-operators");
var point_service_1 = require("../../services/point.service.ts");
var point_1 = require("../../models/point");
var PointCreateComponent = (function () {
    function PointCreateComponent(pointService, location) {
        this.pointService = pointService;
        this.location = location;
        this.model = new point_1.Point();
    }
    PointCreateComponent.prototype.ngOnInit = function () {
    };
    PointCreateComponent.prototype.save = function () {
        var _this = this;
        this.pointService.create(this.model)
            .then(function () { return _this.goBack(); });
    };
    PointCreateComponent.prototype.goBack = function () {
        this.location.back();
    };
    return PointCreateComponent;
}());
PointCreateComponent = __decorate([
    core_1.Component({
        moduleId: module.id,
        selector: 'my-point-create',
        templateUrl: 'point-create.component.html',
        styleUrls: ['point-create.component.css']
    }),
    __metadata("design:paramtypes", [point_service_1.PointService,
        common_1.Location])
], PointCreateComponent);
exports.PointCreateComponent = PointCreateComponent;
//# sourceMappingURL=point-create.component.js.map
