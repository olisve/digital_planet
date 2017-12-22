'use strict';

angular.
module('companyInfo').
component('companyInfo', {
    templateUrl: 'company-info/company-info.template.html',
    controller: ['$http', '$routeParams',
        function CompanyInfoController($http, $routeParams) {
            var self = this;
            var info = $routeParams.infoCategory;
            this.aboutcompany='';
            this.sales='';
            this.payment='';
            this.delivery='';
            this.contacts='';

            if(info == 'aboutcompany')
                this.aboutcompany = true;
            else if(info == 'sales')
                this.sales = true;
            else if(info == 'payment')
                this.payment = true;
            else if(info == 'delivery')
                this.delivery = true;
            else if(info == 'contacts')
                this.contacts = true;
        }
    ]
});