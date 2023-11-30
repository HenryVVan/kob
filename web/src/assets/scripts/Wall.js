import { GameObject } from "./GameObject";

export class Wall extends GameObject {
    // 传递墙的第r行，第c列
    constructor(r, c, gamemap) {
        super();
        this.color = "#B4722A";
        this.r = r;
        this.c = c;
        this.gamemap = gamemap;
    }

    update() {
        this.render();
    }

    render() {
        const L = this.gamemap.L;
        const ctx = this.gamemap.ctx;
        ctx.fillStyle = this.color;
        ctx.fillRect(this.c * L, this.r * L, L, L);
    }


}