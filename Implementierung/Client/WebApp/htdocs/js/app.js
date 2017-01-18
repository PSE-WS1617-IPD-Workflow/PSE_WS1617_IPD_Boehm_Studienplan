
          define("studyplan",["underscore", "backbone", "jquery"],function(_, Backbone, $){
        var edu = {kit:{}};
edu.kit.informatik = {};
edu.kit.informatik.studyplan = {};
edu.kit.informatik.studyplan.client = {};
edu.kit.informatik.studyplan.client.model = {};
edu.kit.informatik.studyplan.client.model.system = {};
edu.kit.informatik.studyplan.client.model.system.LanguageManager = function() {
  var a = null, b = function() {
    this.messages = {};
    this.language = "de";
    this.setLanguage = function(a) {
      this.language = a;
    };
    this.getMessage = function(a) {
      return "undefined" !== typeof this.messages[this.language][a] ? this.messages[this.language][a] : "undefined" !== typeof this.messages.de[a] ? this.messages.de[a] : a;
    };
  };
  return {getInstance:function() {
    null === a && (a = new b);
    return a;
  }};
}();
edu.kit.informatik.studyplan.client.view = {};
edu.kit.informatik.studyplan.client.view.MainView = Backbone.View.extend({setHeader:function(a, b) {
}, setContent:function(a, b) {
}});
edu.kit.informatik.studyplan.client.view.components = {};
edu.kit.informatik.studyplan.client.view.components.filter = {};
edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent = Backbone.View.extend({getId:function() {
}, onSelect:function() {
}, getFilter:function() {
}});
edu.kit.informatik.studyplan.client.view.components.filter.ModuleFilter = Backbone.View.extend({onSearch:function() {
}, getFilters:function() {
}});
edu.kit.informatik.studyplan.client.view.components.filter.RangeFilter = edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent.extend({getParams:function() {
}, onSelect:function() {
}});
edu.kit.informatik.studyplan.client.view.components.filter.SelectFilter = edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent.extend({getParams:function() {
}, onSelect:function() {
}});
edu.kit.informatik.studyplan.client.view.components.filter.TextFilter = edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent.extend({getParams:function() {
}, onSelect:function() {
}});
edu.kit.informatik.studyplan.client.view.components.uielement = {};
edu.kit.informatik.studyplan.client.view.components.uielement.ModuleBox = Backbone.View.extend({setRedBorder:function(a) {
}, removeModule:function() {
}, click:function() {
}});
edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder = Backbone.View.extend({onSearch:function() {
}});
edu.kit.informatik.studyplan.client.view.components.uielement.ModuleInfoSidebar = Backbone.View.extend({onChange:function() {
}, onClose:function() {
}});
edu.kit.informatik.studyplan.client.view.components.uielement.ModuleList = Backbone.View.extend({});
edu.kit.informatik.studyplan.client.view.components.uielement.NotificationBox = Backbone.View.extend({onClose:function() {
}, blurOut:function() {
}});
edu.kit.informatik.studyplan.client.view.components.uielement.PassedModulePlan = Backbone.View.extend({});
edu.kit.informatik.studyplan.client.view.components.uielement.Plan = Backbone.View.extend({addSemester:function() {
}, onChange:function() {
}});
edu.kit.informatik.studyplan.client.view.components.uielement.PlanHeadBar = Backbone.View.extend({onChange:function() {
}});
edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar = edu.kit.informatik.studyplan.client.view.components.uielement.PlanHeadBar.extend({generate:function() {
}, verify:function() {
}, rename:function() {
}});
edu.kit.informatik.studyplan.client.view.components.uielement.Semester = Backbone.View.extend({removeSemester:function() {
}, onDrop:function(a, b) {
}, scrollLeft:function() {
}, scrollRight:function() {
}});
edu.kit.informatik.studyplan.client.view.components.uielement.PlanListElement = Backbone.View.extend({show:function() {
}, "export":function() {
}, duplicate:function() {
}, "delete":function() {
}});
edu.kit.informatik.studyplan.client.view.components.uielement.ProposeHeadBar = edu.kit.informatik.studyplan.client.view.components.uielement.PlanHeadBar.extend({});
edu.kit.informatik.studyplan.client.view.components.uipanel = {};
edu.kit.informatik.studyplan.client.view.components.uipanel.ComparisonView = Backbone.View.extend({});
edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent = {};
edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent = Backbone.View.extend({next:function() {
}});
edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent1 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend({next:function() {
}, onChange:function() {
}});
edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent2 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend({next:function() {
}, onChange:function() {
}});
edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent3 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend({next:function() {
}, onChange:function() {
}});
edu.kit.informatik.studyplan.client.view.components.uipanel.NotificationCentre = Backbone.View.extend({onChange:function() {
}});
edu.kit.informatik.studyplan.client.view.components.uipanel.PlanList = Backbone.View.extend({onActionSelection:function() {
}, onChange:function() {
}});
edu.kit.informatik.studyplan.client.view.components.uipanel.ProposalSidebar = Backbone.View.extend({"delete":function() {
}, save:function() {
}, saveAs:function() {
}});
edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent1 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend({next:function() {
}, onChange:function() {
}});
edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent2 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend({next:function() {
}, onChange:function() {
}});
edu.kit.informatik.studyplan.client.view.subview = {};
edu.kit.informatik.studyplan.client.view.subview.ComparisonPage = Backbone.View.extend({});
edu.kit.informatik.studyplan.client.view.subview.Header = Backbone.View.extend({});
edu.kit.informatik.studyplan.client.view.subview.LoginPage = Backbone.View.extend({});
edu.kit.informatik.studyplan.client.view.subview.MainPage = Backbone.View.extend({addPlan:function() {
}});
edu.kit.informatik.studyplan.client.view.subview.PlanEditPage = Backbone.View.extend({showModuleDetails:function(a) {
}, hideModuleDetails:function() {
}});
edu.kit.informatik.studyplan.client.view.subview.ProfilPage = Backbone.View.extend({close:function() {
}, showModuleDetails:function(a) {
}, hideModuleDetails:function() {
}});
edu.kit.informatik.studyplan.client.view.subview.WizardPage = Backbone.View.extend({next:function() {
}});
edu.kit.informatik.studyplan.client.model.module = {};
edu.kit.informatik.studyplan.client.model.module.Module = Backbone.Model.extend({});
edu.kit.informatik.studyplan.client.model.module.ModuleCollection = Backbone.Collection.extend({url:function() {
}});
edu.kit.informatik.studyplan.client.model.module.ModuleConstraint = Backbone.Model.extend({});
edu.kit.informatik.studyplan.client.model.module.Preference = Backbone.Model.extend({url:function() {
}});

          return edu.kit.informatik.studyplan.client;
      });
      