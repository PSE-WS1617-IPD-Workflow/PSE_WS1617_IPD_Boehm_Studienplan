goog.provide("edu.kit.informatik.studyplan.client.view.components.filter.ModuleFilter");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.filter.ModuleFilter = Backbone.View.extend( /** @lends {edu.kit.informatik.studyplan.client.view.components.filter.ModuleFilter.prototype} */ {
    /**
     * html template for this element
     */
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/filter/moduleFilter.html"),
    /**
     * the tag name of the described html element in the template
     */
    tag: "ul",
    /** ui filter elements */
    filterComponents: null,
    /** pointer to model */
    filterCollection: null,
    /**
     * events triggered by HTML
     */
    events: {
        "click .resetFilters": "resetFilters",
        "click .filterMenuButton": "showFilterSettings",
        "click .filterMenuButton_highlited_FilterButton": "showFilterSettings",
        "change .filterButton": "onChange"
    },
    initialize: function (options) {
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().showLoading();
        this.filterCollection = new edu.kit.informatik.studyplan.client.model.system.FilterCollection();
        this.filterCollection.fetch({
            reset: true,
            success: function () {
                edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
            }.bind(this)
        });
        this.filterCollection = options.filterCollection;
        //this.listenTo(this.filterCollection, "change", this.reload);
        //this.listenTo(this.filterCollection, "add", this.reload);
        //this.listenTo(this.filterCollection, "reset", this.reload);
        this.listenTo(this.filterCollection, "reset", this.reload);
        this.reload();
    },
    /**
     * renders this page
     */
    reload: function () {
        this.filterComponents = [];


        //foreach in filterCollection
        var _this = this;
        this.filterCollection.each(function (el) {
            var tmp_options = {
                filter: el
            };
            /**
             * @type {edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent}
             */
            var uiFilter = null;            
            switch(el.get("specification").type) {
                case "contains":
                    uiFilter = new edu.kit.informatik.studyplan.client.view.components.filter.TextFilter(tmp_options);
                    break;
                case "list":
                    uiFilter = new edu.kit.informatik.studyplan.client.view.components.filter.SelectFilter(tmp_options);
                    break;
                case "range":
                    uiFilter = new edu.kit.informatik.studyplan.client.view.components.filter.RangeFilter(tmp_options);
                    break;
            }
            if (uiFilter !== null) {
                uiFilter.initialize(tmp_options);
                _this.filterComponents.push(uiFilter);
            }
        });
        this.render();
    },
    /**
     * @this {Backbone.View}
     * @return *    
     */
    render: function () {
        "use strict";
        var filterButtons = [];
        _.each(this.filterComponents, function (tmpFilterComponent) {

            if (tmpFilterComponent.filter.get('specification').type !== "contains") {
                filterButtons.push({
                    id: tmpFilterComponent.filter.get("id"),
                    name: tmpFilterComponent.filter.get("name")
                });
            }
        }.bind(this));

        this.$el.html(this.template({
            buttons: filterButtons
        }));

        var finder = this.$el.find(".collectivefilterSettings");
        for (var i = 0; i < this.filterComponents.length; i++) {
            var tmpFilterComponent = this.filterComponents[i];
            tmpFilterComponent.render();
            finder.append(tmpFilterComponent.$el);
        }
        this.delegateEvents();
    },

    /**
     * triggered by changeEvent
     */
    onChange: function () {
        "use strict";
        this.render();
    },
    resetFilters: function(event){
        this.filterCollection.each(function (el) {
            el.set("curValue", el.get("default-value"));
        });
        this.render();
        $(".filterButton").click();
    },
    buildParam: function () {},
    /** displays the filter's settings in HTML*/
    showFilterSettings: function (event) {
        var tmpVisible = $("#filterId_" + event.target.id).is(":visible")

        $(".highlited_FilterButton").removeClass("highlited_FilterButton");
        $(".profileFilterWrapperSettings").hide();
        if (!tmpVisible) {
            $("#filterId_" + event.target.id).show();
            $("#" + event.target.id).addClass("highlited_FilterButton");
        }

    }
});
