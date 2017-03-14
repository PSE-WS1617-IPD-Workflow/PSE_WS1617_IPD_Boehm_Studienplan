(function () {
    var LM = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();
    var TM = edu.kit.informatik.studyplan.client.model.system.TourManager.getInstance();
    TM.tours["profilePage"] = function () {
        var tour;

        tour = new Shepherd.Tour({
          defaults: {
            classes: 'shepherd-theme-arrows',
            scrollTo: true
          }
        });
        tour.addStep('step1', {
          text: LM.getMessage("TOUR-ProfilePage.welcome"),
          showCancelLink:true,
          buttons: [
            {
              text: LM.getMessage("TOUR-next"),
              action: tour.next
            }
          ]
        });
        tour.addStep('step2', {
          text: LM.getMessage("TOUR-ProfilePage.plan"),
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
        tour.addStep('step3', {
          text: LM.getMessage("TOUR-ProfilePage.moduleFilter"),
          attachTo: ".profileModuleFinderWrapper right",
          showCancelLink:true,
          buttons: [
            {
              text: LM.getMessage("TOUR-next"),
              action: tour.next
            }
          ]
        });
        tour.addStep('step4', {
          text: LM.getMessage("TOUR-ProfilePage.moduleList"),
          attachTo: ".profileModuleCollectionWrapper left",
          showCancelLink:true,
          buttons: [
            {
              text: LM.getMessage("TOUR-next"),
              action: tour.next
            }
          ]
        });
        tour.addStep('step5', {
          text: LM.getMessage("TOUR-ProfilePage.deleteProfile"),
          attachTo: "#deleteUser top",
          showCancelLink:true,
          buttons: [
            {
              text: LM.getMessage("TOUR-next"),
              action: tour.next
            }
          ]
        });
        tour.addStep('step6', {
          text: LM.getMessage("TOUR-ProfilePage.save"),
          attachTo: "#save top",
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
