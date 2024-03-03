import { GameObject } from "./GameObject";
import { Cell } from "./Cell";

export class Snake extends GameObject {
    constructor(info, gamemap) {
        super();

        this.id = info.id;
        this.color = info.color;
        this.gamemap = gamemap;

        // 存放蛇的身体，cells[0]存放蛇头
        this.cells = [new Cell(info.r, info.c)];
        this.next_cell = null;


        // 蛇每秒走5个格子
        this.speed = 5;
        // -1 表示没有指令，0 1 2 3 表示上右下左
        this.direction = -1;
        // idle 静止 move 移动 die 已经死亡
        this.status = "idle";

        this.dr = [-1, 0, 1, 0]; // 行偏移量
        this.dc = [0, 1, 0, -1]; // 列偏移量

        this.step = 0; // 表示当前回合数
        this.eps = 1e-2; // 允许的误差

        // 初始化蛇眼睛
        // 左下角的蛇初始眼睛朝上
        this.eye_direction = 0;
        // 右上角的蛇初始眼睛朝下
        if (this.id == 1) this.eye_direction = 2;

        // 蛇眼睛x方向 ：不同方向的偏移量
        this.eye_dx = [
            [-1, 1],
            [1, 1],
            [-1, 1],
            [-1, -1]
        ];

        // 蛇眼睛y方向： 不同方向的偏移量
        this.eye_dy = [
            [-1, -1],
            [-1, 1],
            [1, 1],
            [-1, 1]
        ];
    }

    start() {

    }

    // 检测当前回合，蛇的长度是否增加
    check_tail_increasing() {
        if (this.step <= 10) return true;
        if (this.step % 3 === 1) return true;
        return false;
    }

    set_direction(d) {
        this.direction = d;
    }

    // 将蛇的状态变为走下一步
    next_step() {
        console.log(6);
        const d = this.direction; // 获取蛇的方向
        // console.log(d);
        const nextCell = new Cell(this.cells[0].r + this.dr[d], this.cells[0].c + this.dc[d]);
        if (this.gamemap.check_vaild(nextCell)) {
            this.next_cell = nextCell;
            this.status = "move";
            this.eye_direction = d;
            this.direction = -1;
            // console.log(7);

            this.step++;
            const k = this.cells.length;
            // 头部不变
            for (let i = k; i > 0; i--) {
                // 防止引用间互相干扰，使用json转
                this.cells[i] = JSON.parse(JSON.stringify(this.cells[i - 1]));
            }
        }
        else {
            this.status = "die";
        }

        // console.log(8);
    }

    update_move() {
        console.log(1);
        const dx = this.next_cell.x - this.cells[0].x;
        const dy = this.next_cell.y - this.cells[0].y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        // console.log(2);
        // 说明已经重合了
        if (distance < this.eps) {
            this.cells[0] = this.next_cell; // 添加一个新蛇头
            this.next_cell = null;

            this.status = "idle"; // 走完了， 停下了

            // console.log(3);
            // 蛇不变长
            if (!this.check_tail_increasing()) {
                this.cells.pop();
            }
            // console.log(4);

        }
        else {
            const move_distance = this.speed * this.timedelta / 1000;
            this.cells[0].x += move_distance * dx / distance;
            this.cells[0].y += move_distance * dy / distance;
            // console.log(5);
            if (!this.check_tail_increasing()) {
                const k = this.cells.length;
                // console.log(k + "xxxx");
                // console.log(k);
                const tail = this.cells[k - 1], tail_target = this.cells[k - 2];
                const tail_dx = tail_target.x - tail.x;
                const tail_dy = tail_target.y - tail.y;
                tail.x += move_distance * tail_dx / distance;
                tail.y += move_distance * tail_dy / distance;
            }
        }
    }

    // 每一帧执行一次
    update() {
        console.log(this.cells); // 打印当前的 cells 信息
        if (this.status === "move") {
            console.log("move");
            this.update_move();
        }
        this.render();
    }
    render() {
        // console.log(1111);
        const L = this.gamemap.L;
        const ctx = this.gamemap.ctx;

        // 后渲染的颜色会覆盖前面渲染的颜色
        ctx.fillStyle = this.color;

        // console.log(2222);
        if (this.status === "die") {
            ctx.fillStyle = "white";
        }

        // if (!this.cells || this.cells.length === 0) {
        //     return; // 如果cells为空，则不进行渲染
        // }

        // console.log(3333);

        for (const cell of this.cells) {
            ctx.beginPath();
            ctx.arc(cell.x * L, cell.y * L, L / 2 * 0.8, 0, Math.PI * 2);
            ctx.fill();
        }

        // console.log(4444);
        // 美化蛇的身体
        for (let i = 1; i < this.cells.length; i++) {
            const a = this.cells[i - 1], b = this.cells[i];
            if (Math.abs(a.x - b.x) < this.eps && Math.abs(a.y - b.y) < this.eps)
                continue;
            // 竖直方向
            if (Math.abs(a.x - b.x) < this.eps) {
                ctx.fillRect((a.x - 0.4) * L, Math.min(a.y, b.y) * L, L * 0.8, Math.abs(a.y - b.y) * L);
            }
            // 水平方向
            else {
                ctx.fillRect(Math.min(a.x, b.x) * L, (a.y - 0.4) * L, Math.abs(a.x - b.x) * L, L * 0.8);
            }
        }

        // console.log(5555);
        ctx.fillStyle = "black";
        for (let i = 0; i < 2; i++) {
            const eye_x = (this.cells[0].x + this.eye_dx[this.eye_direction][i] * 0.15) * L;
            const eye_y = (this.cells[0].y + this.eye_dy[this.eye_direction][i] * 0.15) * L;
            ctx.beginPath();
            ctx.arc(eye_x, eye_y, L * 0.05, 0, Math.PI * 2);
            ctx.fill();
        }
    }
}