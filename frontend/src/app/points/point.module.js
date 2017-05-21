"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var point_detail_component_1 = require("./edit/point-detail-component");
var points_component_1 = require("./points.component");
var point_service_1 = require("./../services/point.service.ts");
var point_create_component_1 = require("./create/point-create.component");
var forms_1 = require("@angular/forms");
var platform_browser_1 = require("@angular/platform-browser");
var router_1 = require("@angular/router");
var PointModule = (function () {
    function PointModule() {
    }
    return PointModule;
}());
PointModule = __decorate([
    core_1.NgModule({
        imports: [
            forms_1.FormsModule,
            platform_browser_1.BrowserModule,
            router_1.RouterModule
        ],
        declarations: [
            point_detail_component_1.PointDetailComponent,
            point_create_component_1.PointCreateComponent,
            points_component_1.PointComponent,
        ],
        providers: [
            point_service_1.PointService
        ],
    })
], PointModule);
exports.PointModule = PointModule;
//# sourceMappingURL=point.module.js.map
