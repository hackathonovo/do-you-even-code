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
require("../rxjs-operators");
var plan_session_1 = require("../plan-session");
var statistic_service_1 = require("../services/statistic.service");
var ng2_charts_1 = require("ng2-charts");
var hotel_service_1 = require("../services/hotel.service");
var StatisticsComponent = (function () {
    function StatisticsComponent(router, service, hotelService) {
        var _this = this;
        this.router = router;
        this.service = service;
        this.hotelService = hotelService;
        this.title = 'Statistics';
        this.selectedHotelId = 0;
        // PolarArea
        this.polarAreaLegend = true;
        this.polarAreaChartType = 'pie';
        this.lineChartOptions = {
            responsive: true
        };
        this.foodLabels = [];
        this.foodData = [];
        this.activityLabels = [];
        this.activityData = [];
        this.imageLabels = [];
        this.imageData = [];
        this.transportLabels = [];
        this.transportData = [];
        this.user = plan_session_1.user;
        this.hotelService.getHotels().subscribe(function (data) {
            _this.hotels = data;
        }, function (error) { return _this.errorMessage = error; });
        this.mapFoodData();
        this.mapActivityData();
        this.mapImageData();
        this.mapTransportData();
    }
    StatisticsComponent.prototype.mapFoodData = function () {
        var _this = this;
        this.service.getFoodStatistics().subscribe(function (data) {
            for (var _i = 0, data_1 = data; _i < data_1.length; _i++) {
                var item = data_1[_i];
                _this.foodLabels.push(item.naziv + "(" + item.hotel_name + ")");
                _this.foodData.push(item.cnt * 100);
            }
            _this.updateChart();
            console.debug(_this.foodLabels);
            console.debug(_this.foodData);
        }, function (error) { return _this.errorMessage = error; });
    };
    StatisticsComponent.prototype.mapHotelFoodData = function () {
        var _this = this;
        this.foodData = [];
        this.foodLabels = [];
        this.service.getHotelFoodStatistics(this.selectedHotelId).subscribe(function (data) {
            for (var _i = 0, data_2 = data; _i < data_2.length; _i++) {
                var item = data_2[_i];
                _this.foodLabels.push(item.naziv + "(" + item.hotel_name + ")");
                _this.foodData.push(item.cnt * 100);
            }
            _this.updateChart();
            console.debug(_this.foodLabels);
            console.debug(_this.foodData);
        }, function (error) { return _this.errorMessage = error; });
    };
    StatisticsComponent.prototype.mapActivityData = function () {
        var _this = this;
        this.service.getActivityStatistics().subscribe(function (data) {
            for (var _i = 0, data_3 = data; _i < data_3.length; _i++) {
                var item = data_3[_i];
                _this.activityLabels.push(item.naziv + "(" + item.hotel_name + ")");
                _this.activityData.push(item.cnt * 100);
            }
            _this.updateChart();
            console.debug(_this.activityLabels);
            console.debug(_this.activityData);
        }, function (error) { return _this.errorMessage = error; });
    };
    StatisticsComponent.prototype.mapHotelActivityData = function () {
        var _this = this;
        this.activityLabels = [];
        this.activityData = [];
        this.service.getHotelActivityStatistics(this.selectedHotelId).subscribe(function (data) {
            for (var _i = 0, data_4 = data; _i < data_4.length; _i++) {
                var item = data_4[_i];
                _this.activityLabels.push(item.naziv + "(" + item.hotel_name + ")");
                _this.activityData.push(item.cnt * 100);
            }
            _this.updateChart();
            console.debug(_this.activityLabels);
            console.debug(_this.activityData);
        }, function (error) { return _this.errorMessage = error; });
    };
    StatisticsComponent.prototype.mapImageData = function () {
        var _this = this;
        this.imageLabels = [];
        this.imageData = [];
        this.service.getImageStatistics().subscribe(function (data) {
            for (var _i = 0, data_5 = data; _i < data_5.length; _i++) {
                var item = data_5[_i];
                _this.imageLabels.push(item.naziv + "(" + item.hotel_name + ")");
                _this.imageData.push(item.cnt * 100);
            }
            _this.updateChart();
            console.debug(_this.imageLabels);
            console.debug(_this.imageData);
        }, function (error) { return _this.errorMessage = error; });
    };
    StatisticsComponent.prototype.mapHotelImageData = function () {
        var _this = this;
        this.imageLabels = [];
        this.imageData = [];
        this.service.getHotelImageStatistics(this.selectedHotelId).subscribe(function (data) {
            for (var _i = 0, data_6 = data; _i < data_6.length; _i++) {
                var item = data_6[_i];
                _this.imageLabels.push(item.naziv + "(" + item.hotel_name + ")");
                _this.imageData.push(item.cnt * 100);
            }
            _this.updateChart();
            console.debug(_this.imageLabels);
            console.debug(_this.imageData);
        }, function (error) { return _this.errorMessage = error; });
    };
    StatisticsComponent.prototype.mapTransportData = function () {
        var _this = this;
        this.service.getTransportStatistics().subscribe(function (data) {
            for (var _i = 0, data_7 = data; _i < data_7.length; _i++) {
                var item = data_7[_i];
                _this.transportLabels.push(item.naziv + "(" + item.hotel_name + ")");
                _this.transportData.push(item.cnt * 100);
            }
            _this.updateChart();
            console.debug(_this.transportLabels);
            console.debug(_this.transportData);
        }, function (error) { return _this.errorMessage = error; });
    };
    StatisticsComponent.prototype.mapHotelTransportData = function () {
        var _this = this;
        this.transportLabels = [];
        this.transportData = [];
        this.service.getHotelTransportStatistics(this.selectedHotelId).subscribe(function (data) {
            for (var _i = 0, data_8 = data; _i < data_8.length; _i++) {
                var item = data_8[_i];
                _this.transportLabels.push(item.naziv + "(" + item.hotel_name + ")");
                _this.transportData.push(item.cnt * 100);
            }
            _this.updateChart();
            console.debug(_this.transportLabels);
            console.debug(_this.transportData);
        }, function (error) { return _this.errorMessage = error; });
    };
    StatisticsComponent.prototype.updateChart = function () {
        console.debug(this.charts);
        for (var _i = 0, _a = this.charts.toArray(); _i < _a.length; _i++) {
            var chart = _a[_i];
            chart.ngOnChanges({});
        }
    };
    StatisticsComponent.prototype.selectHotel = function (id) {
        this.selectedHotelId = id;
        if (id == 0) {
            this.mapFoodData();
            this.mapActivityData();
            this.mapImageData();
            this.mapTransportData();
        }
        else {
            this.mapHotelFoodData();
            this.mapHotelActivityData();
            this.mapHotelImageData();
            this.mapHotelTransportData();
        }
    };
    // events
    StatisticsComponent.prototype.chartClicked = function (e) {
        //console.log(e);
    };
    StatisticsComponent.prototype.chartHovered = function (e) {
        //console.log(e);
    };
    StatisticsComponent.prototype.goHome = function () {
        this.router.navigate(['/home']);
    };
    return StatisticsComponent;
}());
__decorate([
    core_1.ViewChildren(ng2_charts_1.BaseChartDirective),
    __metadata("design:type", core_1.QueryList)
], StatisticsComponent.prototype, "charts", void 0);
StatisticsComponent = __decorate([
    core_1.Component({
        moduleId: module.id,
        selector: 'statistics',
        templateUrl: 'statistics.component.html',
        styleUrls: ['statistics.component.css'],
    }),
    __metadata("design:paramtypes", [router_1.Router,
        statistic_service_1.StatisticService,
        hotel_service_1.HotelService])
], StatisticsComponent);
exports.StatisticsComponent = StatisticsComponent;
//# sourceMappingURL=statistics.component.js.map