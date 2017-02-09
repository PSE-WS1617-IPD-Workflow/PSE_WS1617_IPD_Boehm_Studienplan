goog.provide("edu.kit.informatik.studyplan.client.view.components.filter.RangeFilter");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent}
 */

edu.kit.informatik.studyplan.client.view.components.filter.RangeFilter = edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.filter.RangeFilter.prototype}*/{
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/filter/rangeFilter.html"),
    tag: "ul",
    render: function () {
        edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent.prototype.render.apply(this, arguments);
        
        this.$el.find(".rangeSlider").slider({
            range: true,
            min: this.filter.get("specification").min,
            max: this.filter.get("specification").max,
            values: [ this.filter.get("curValue").min, this.filter.get("curValue").max ],
            slide: this.updateVal.bind(this)
        });
        
      this.delegateEvents();
    },
    updateVal: function (event, ui) {
        this.filter.get("curValue").min = ui.values[ 0 ];
        this.filter.get("curValue").max = ui.values[ 1 ];
        
        $("#min_range_" + this.filter.get("id")).val(ui.values[ 0 ]);
        $("#max_range_" + this.filter.get("id")).val(ui.values[ 1 ]);
    }
});