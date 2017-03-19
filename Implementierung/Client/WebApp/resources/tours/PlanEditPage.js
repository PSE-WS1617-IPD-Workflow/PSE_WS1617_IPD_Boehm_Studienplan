(function () {
    var LM = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();
    var TM = edu.kit.informatik.studyplan.client.model.system.TourManager.getInstance();
    TM.tours["planEditPage"] = function () {
        var tour;

        tour = new Shepherd.Tour({
          defaults: {
            classes: 'shepherd-theme-arrows',
            scrollTo: true
          }
        });
        tour.addStep('step1', {
          text: LM.getMessage("TOUR-PlanEditPage.welcome"),
          showCancelLink:true,
          buttons: [
            {
              text: LM.getMessage("TOUR-next"),
              action: tour.next
            }
          ]
        });
        tour.addStep('step2', {
          text: LM.getMessage("TOUR-PlanEditPage.editName"),
          attachTo: "#curPlanName top",
          showCancelLink:true,
          buttons: [
            {
              text: LM.getMessage("TOUR-next"),
              action: tour.next
            }
          ]
        });
        tour.addStep('step3', {
          text: LM.getMessage("TOUR-PlanEditPage.plan"),
          attachTo: ".planView left",
          showCancelLink:true,
          buttons: [
            {
              text: LM.getMessage("TOUR-next"),
              action: tour.next
            }
          ],
          tetherOptions: {
              attachment: 'middle center',
              targetAttachment:'top right',
              targetOffset: '250px -300px'
          }
        });
        tour.addStep('step4', {
          text: LM.getMessage("TOUR-PlanEditPage.addSemester"),
          attachTo: ".addSemesterButton left",
          showCancelLink:true,
          buttons: [
            {
              text: LM.getMessage("TOUR-next"),
              action: tour.next
            }
          ]
        });
        tour.addStep('step5', {
          text: LM.getMessage("TOUR-PlanEditPage.moduleFilter"),
          attachTo: ".profileModuleFinderWrapper right",
          showCancelLink:true,
          buttons: [
            {
              text: LM.getMessage("TOUR-next"),
              action: tour.next
            }
          ]
        });
        tour.addStep('step6', {
          text: LM.getMessage("TOUR-PlanEditPage.moduleList"),
          attachTo: ".profileModuleCollectionWrapper left",
          showCancelLink:true,
          buttons: [
            {
              text: LM.getMessage("TOUR-next"),
              action: tour.next
            }
          ]
        });
        tour.addStep('step7', {
          text: LM.getMessage("TOUR-PlanEditPage.preferences"),
          attachTo: ".profileModuleCollectionWrapper .preferenceButtonBox left",
          showCancelLink:true,
          buttons: [
            {
              text: LM.getMessage("TOUR-next"),
              action: tour.next
            }
          ]
        });
        tour.addStep('step8', {
          text: LM.getMessage("TOUR-PlanEditPage.verification"),
          attachTo: "#verifyPlan top",
          showCancelLink:true,
          buttons: [
            {
              text: LM.getMessage("TOUR-next"),
              action: tour.next
            }
          ]
        });
        tour.addStep('step9', {
          text: LM.getMessage("TOUR-PlanEditPage.generation"),
          attachTo: "#generatePlan top",
          showCancelLink:true,
          buttons: [
            {
              text: LM.getMessage("TOUR-cancel"),
              action: tour.next
            }
          ]
        });
        return tour;
    }
}());