/**
 * 
 */
// https://segmentfault.com/a/1190000005851663
angularDataApp.directive('expander_1', function() {
			return {
				restrict : 'EA',
				replace : true,
				template : "<div style=\"color:red;\">" + "testDirective" +"</div>"
			}
		}).directive('expander_2', function() {
			return {
				restrict : 'EA',
				replace : true,
				transclude : true,
				scope : {
					title : '=expanderTitle'
				},
				template : '<div>'
						+ '<div class="title" ng-click="toggle()">{{title}}</div>'
						+ '<div class="body" ng-show="showMe" ng-transclude></div>'
						+ '</div>',
				link : function(scope, element, attrs) {
					scope.showMe = false;
					scope.toggle = function toggle() {
						scope.showMe = !scope.showMe;
					}
				}
			}
		}).directive('accordion', function() {
			return {
				restrict : 'EA',
				replace : true,
				transclude : true,
				template : '<div ng-transclude></div>',
				controller : function() {
					var expanders = [];
					this.gotOpened = function(selectedExpander) {
						angular.forEach(expanders, function(expander) {
							if (selectedExpander != expander) {
								expander.showMe = false;
							}
						});
					}
					this.addExpander = function(expander) {
						expanders.push(expander);
					}
				}
			}
		}).directive('expander', function() {
			return {
				restrict : 'EA',
				replace : true,
				transclude : true,
				require : '^?accordion',
				scope : {
					title : '=expanderTitle'
				},
				template : '<div>'
						+ '<div class="title" ng-click="toggle()">{{title}}</div>'
						+ '<div class="body" ng-show="showMe" ng-transclude></div>'
						+ '</div>',
				link : function(scope, element, attrs, accordionController) {
					scope.showMe = false;
					accordionController.addExpander(scope);
					scope.toggle = function toggle() {
						scope.showMe = !scope.showMe;
						accordionController.gotOpened(scope);
					}
				}
			}
		});

/* .directive('onRepeatFinishedRender',
		function($timeout) {
			return {
				restrict : 'A',
				link : function(scope, element, attr) {
					if (scope.$last === true) {
						$timeout(function() {
							scope.$emit('ngRepeatFinished', element);
						});
					}
				}
			};
		}).directive('datePicker', function ($filter) {
		    return {
		        require: 'ngModel',
		        link: function (scope, element, attr, ngModel) {
		            $(element).datetimepicker({
		           	 locale: 'zh-cn',
		             useCurrent: false, //Important! See issue #1075
		             sideBySide: true,
		             showTodayButton: true
		            });
		            $(element).on("dp.change", function (e) {
		                ngModel.$viewValue = $filter('date')(e.date.valueOf(), 'yyyy-MM-dd HH:mm:ss');
		                if(ngModel.$viewValue==false)
		                {
		                	ngModel.$viewValue="";
		                }
		                ngModel.$commitViewValue();		            
		            });
		        }
		    };
		}).directive('datePickerInput', function() {
		    return {
		        require: 'ngModel',
		        link: function (scope, element, attr, ngModel) {
		            // Trigger the Input Change Event, so the Datepicker gets refreshed
		            scope.$watch(attr.ngModel, function (value) {
		                if (value) {
		                    element.trigger("change");
		                }
		            });
		        }
		    };
		}).directive('datePickerdate', function ($filter) {
		    return {
		        require: 'ngModel',
		        link: function (scope, element, attr, ngModel) {
		            $(element).datetimepicker({
		           	 locale: 'zh-cn',
		             useCurrent: false, //Important! See issue #1075
		             sideBySide: true,
		             showTodayButton: true,
		             format: 'YYYY-MM-DD',
		            });
		            $(element).on("dp.change", function (e) {
		                ngModel.$viewValue = $filter('date')(e.date.valueOf(), 'yyyy-MM-dd');
		                if(ngModel.$viewValue==false)
		                {
		                	ngModel.$viewValue="";
		                }
		                ngModel.$commitViewValue();	        
		            });
		        }
		    };
		}).directive('datePickerdateInput', function() {
		    return {
		        require: 'ngModel',
		        link: function (scope, element, attr, ngModel) {
		            // Trigger the Input Change Event, so the Datepicker gets refreshed
		            scope.$watch(attr.ngModel, function (value) {
		                if (value) {
		                    element.trigger("change");
		                }
		            });
		        }
		    };
		});*/