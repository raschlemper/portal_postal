'use strict';

app.controller('ReportController', ['$scope', '$sce', '$stateParams', 'PDFViewerService',
    function ($scope, $sce, $stateParams, PDFViewerService) {
            
        var init = function () {
            $scope.viewer = PDFViewerService.Instance("viewer");
            var report = $stateParams.report;
            var fileURL = report;
            $scope.pdfContent = $sce.trustAsResourceUrl("blob:http%3A//localhost%3A9090/608d2ad1-7a46-4746-972f-7123747dd3fa");
//            document.getElementById('visualizador').setAttribute('src',fileURL); 
        }; 
        
        init();

    }]);

