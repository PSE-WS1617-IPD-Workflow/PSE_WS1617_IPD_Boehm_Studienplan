goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.ModuleBox");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 * Object which represents a Module Box
 */

edu.kit.informatik.studyplan.client.view.components.uielement.ModuleBox = Backbone.View.extend( /** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.ModuleBox.prototype} */ {
    model: null,
    tagName: "li",
    className: "moduleBox",
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/moduleBox.html"),
    isPreferencable: true,
    isDraggable: true,
    isRemovable: true,
    isPassedPlanModule: false,
    /**
     * events which are listened to and their handlers
     */
    events: {
        "click .moduleBoxLink": "click",
        "click button.deleteButton": "removeModule",
        "click button.preferenceButtonUp": "voteUp",
        "click button.preferenceButtonDown": "voteDown"
    },
    initialize: function (options) {
        this.model = options.module;
        this.isRemovable = (typeof options.isRemovable !== "undefined") ? options.isRemovable : this.isRemovable;
        this.isDraggable = (typeof options.isDraggable !== "undefined") ? options.isDraggable : this.isDraggable;
        this.isPreferencable = (typeof options.isPreferencable !== "undefined") ? options.isPreferencable : this.isPreferencable;
        this.isPassedPlanModule = (typeof options.isPassedPlanModule !== "undefined") ? options.isPassedPlanModule : this.isPassedPlanModule;
        this.listenTo(this.model, "change", this.render);
    },
    /**
     * Method called when upvoted
     */
    voteUp: function () {
        "use strict";
        //console.info("[edu.kit.informatik.studyplan.client.view.components.uielement.ModuleBox] voteUp");
        var preference = this.model.get('preference');
        preference.set('preference', 'positive');
        preference.save();
    },
    /** 
     * Method called when downvoted
     */
    voteDown: function () {
        //console.info("[edu.kit.informatik.studyplan.client.view.components.uielement.ModuleBox] voteDown");
        var preference = this.model.get('preference');
        preference.set('preference', 'negative');
        preference.save();
    },
    /**
     * Method which sets a red border to the object (for verification result)
     * @param {boolean} setBorder Whether or not the border should be set
     */
    setRedBorder: function (setBorder) {
        if (setBorder) {
            this.$el.addClass("invalid");
        } else {
            this.$el.removeClass("invalid")
        }
    },

    /**
     * Method which removes the module from the current collection
     */
    removeModule: function () {
        "use strict";
        // TODO zeug
        //console.info("[edu.kit.informatik.studyplan.client.view.components.uielement.ModuleBox] remove");
        var oldCol = this.model.collection;
        if (this.isPassedPlanModule) {
            this.model.collection.remove(this.model);
        } else {
            this.model.destroy();
        }
    },
    /**
     * Method which is executed once someone clicks on the module
     */
    click: function (event) {
        "use strict";
        event.preventDefault();
        var eventBus = edu.kit.informatik.studyplan.client.model.system.EventBus;
        eventBus.trigger("showModuleInfo", this.model)
    },
    render: function () {
        this.$el.addClass((this.isDraggable) ? 'draggable' : '');
        this.$el.html(this.template({
            module: this.model,
            isRemovable: this.isRemovable,
            isDraggable: this.isDraggable,
            isPreferencable: this.isPreferencable
        }));
        if (this.isDraggable) {
            this.$el.draggable({
                scroll: true,
                opacity: 0.7,
                helper: "clone",
                appendTo: 'body',
                //containment: 'window',
                start: function (event, ui) {
                    ui.helper.data("viewObject", this);
                    ui.helper.addClass("grabbing");
                }.bind(this)
            });
        }
        this.delegateEvents();
    }
});