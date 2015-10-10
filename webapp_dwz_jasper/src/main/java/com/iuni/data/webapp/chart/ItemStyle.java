package com.iuni.data.webapp.chart;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ItemStyle {

    private Normal normal;
    private Emphasis emphasis;

    public ItemStyle(){
        normal = new Normal();
        emphasis = new Emphasis();
        Label label = new Label();
        label.setShow(true);
        normal.setLabel(label);
        emphasis.setLabel(label);
    }

    public Normal getNormal() {
        return normal;
    }

    public void setNormal(Normal normal) {
        this.normal = normal;
    }

    public Emphasis getEmphasis() {
        return emphasis;
    }

    public void setEmphasis(Emphasis emphasis) {
        this.emphasis = emphasis;
    }

    class Normal{
        private Label label;

        public Label getLabel() {
            return label;
        }

        public void setLabel(Label label) {
            this.label = label;
        }

    }

    class Emphasis{
        private Label label;

        public Label getLabel() {
            return label;
        }

        public void setLabel(Label label) {
            this.label = label;
        }

    }

    class Label{
        private boolean show;

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }
    }

}
