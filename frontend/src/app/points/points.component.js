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
var point_service_1 = require("./../services/point.service.ts");
require("../rxjs-operators");
var PointComponent = (function () {
    function PointComponent(pointService) {
        this.pointService = pointService;
        this.getList();
    }
    PointComponent.prototype.ngOnInit = function () {
    };
    PointComponent.prototype.getList = function () {
        var _this = this;
        this.pointService.list().subscribe(function (list) { return _this.list = list; }, function (error) { return _this.errorMessage = error; });
    };
    PointComponent.prototype.remove = function (id) {
        var _this = this;
        if (confirm("Are you sure you wish to delete this Point?")) {
            this.pointService.remove(id)
                .then(function () {
                _this.getList();
            });
        }
    };
    return PointComponent;
}());
PointComponent = __decorate([
    core_1.Component({
        moduleId: module.id,
        selector: 'my-points',
        templateUrl: 'points.component.html',
        styleUrls: ['points.component.css'],
        providers: [point_service_1.PointService]
    }),
    __metadata("design:paramtypes", [point_service_1.PointService])
], PointComponent);
exports.PointComponent = PointComponent;
//# sourceMappingURL=points.component.js.map
