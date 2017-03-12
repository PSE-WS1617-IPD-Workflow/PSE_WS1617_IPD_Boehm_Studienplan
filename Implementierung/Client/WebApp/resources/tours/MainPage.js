(function () {
    var LM = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();
    var TM = edu.kit.informatik.studyplan.client.model.system.TourManager.getInstance();
    TM.tours["mainPage"] = function () {
        var tour;

        tour = new Shepherd.Tour({
          defaults: {
            classes: 'shepherd-theme-arrows',
            scrollTo: true
          }
        });
        tour.addStep('step1', {
          text: LM.getMessage("TOUR-MainPage.welcome"),
          attachTo: "#header top",
          buttons: [
            {
              text: LM.getMessage("TOUR-next"),
              action: tour.next
            }
          ]
        });
        tour.addStep('step2', {
          text: LM.getMessage("TOUR-MainPage.table"),
          attachTo: ".planList top",
          buttons: [
            {
              text: LM.getMessage("TOUR-next"),
              action: tour.next
            }
          ]
        });
        tour.addStep('step3', {
          text: LM.getMessage("TOUR-MainPage.mainPageAddPlan"),
          attachTo: ".mainPageAddPlan right",
          buttons: [
            {
              text: LM.getMessage("TOUR-done"),
              action: tour.next
            }
          ]
        });
        return tour;
    }
}());