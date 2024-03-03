import { GameObject } from "./GameObject";
import { Snake } from "./Snake";
import { Wall } from "./Wall";

export class GameMap extends GameObject {
    // ctx 画布
    constructor(ctx, parent, store) {
        super();

        this.ctx = ctx;
        this.parent = parent;
        this.store = store;
        this.L = 0; // L表示一个单位的长度

        this.rows = 13;
        this.cols = 14;

        // 随机障碍物数量，关于主对角线对称
        this.inner_walls_count = 10;
        this.walls = [];

        this.snakes = [
            new Snake({ id: 0, color: "#4876EC", r: this.rows - 2, c: 1 }, this),
            new Snake({ id: 1, color: "#F94848", r: 1, c: this.cols - 2 }, this),
        ]

        // // 左下角的蛇初始眼睛朝上
        // this.eye_direction = 0;
        // // 右上角的蛇初始眼睛朝下
        // if (this.id == 1) this.eye_direction = 2;

    }

    create_walls() {
        const g = this.store.state.pk.gamemap;

        for (let r = 0; r < this.rows; r++) {
            for (let c = 0; c < this.cols; c++) {
                if (g[r][c]) {
                    this.walls.push(new Wall(r, c, this));
                }
            }
        }
    }

    add_listening_events() {
        console.log(this.store.state.record);
        // 如果是播放回放的话
        if (this.store.state.record.is_record) {
            const a_steps = this.store.state.record.a_steps.split(',');
            const b_steps = this.store.state.record.b_steps.split(',');

            // test code
            // console.log(1111);
            // console.log(a_steps);
            // console.log(b_steps);
            // console.log(1111);

            const loser = this.store.state.record.record_loser;
            const [snake0, snake1] = this.snakes;
            let k = 0;
            const interval_id = setInterval(() => {
                // 最后一步是蛇死亡，不用复现
                if (k >= Math.min(a_steps.length - 1, b_steps.length - 1)) {
                    if (loser === "all" || loser === "A") {
                        snake0.status = "die";
                    }
                    if (loser === "all" || loser === "B") {
                        snake1.status = "die";
                    }
                    clearInterval(interval_id);
                }
                else {
                    // 在这里，我们确保从字符串数组中解析出的每个步骤都是整数
                    const dirA = parseInt(a_steps[k], 10);
                    const dirB = parseInt(b_steps[k], 10);

                    // 只有当解析结果是有效数字时，才设置方向
                    if (!isNaN(dirA)) {
                        snake0.set_direction(dirA);
                    }
                    if (!isNaN(dirB)) {
                        snake1.set_direction(dirB);
                    }
                }
                k++;
            }, 300); // 300ms执行一次
        }
        else {
            // 获取自动聚焦
            this.ctx.canvas.focus();

            this.ctx.canvas.addEventListener("keydown", e => {
                console.log(e.key);
                let d = -1;
                if (e.key === 'w') d = 0;
                else if (e.key === 'd') d = 1;
                else if (e.key === 's') d = 2;
                else if (e.key === 'a') d = 3;


                // 有效输入
                if (d >= 0) {
                    // 将json转成字符串
                    this.store.state.pk.socket.send(JSON.stringify({
                        event: "move",
                        direction: d,
                    }))
                }
            });
        }
    }

    start() {
        //不用循环生成
        this.create_walls();
        this.add_listening_events();
    }

    check_ready() {
        for (const snake of this.snakes) {
            if (snake.status !== "idle") return false;
            if (snake.direction === -1) return false;
        }
        return true;
    }

    update_size() {
        // 计算小正方形的边长，L需要取整数，不然墙与墙之间会有缝隙
        this.L = parseInt(Math.min(this.parent.clientWidth / this.cols, this.parent.clientHeight / this.rows));
        this.ctx.canvas.width = this.L * this.cols;
        this.ctx.canvas.height = this.L * this.rows;
    }

    // 让两条蛇进入下一回合
    next_step() {
        for (const snake of this.snakes) {
            snake.next_step();
        }
    }

    // 检测目标位置是否合法, 两蛇身体和墙没有相撞
    check_vaild(cell) {
        // 判断是否越界
        if (cell.r < 0 || cell.r >= this.rows || cell.c < 0 || cell.c >= this.cols) {
            return false;
        }
        // 判断是否与障碍物相撞
        for (const wall of this.walls) {
            if (wall.r === cell.r && wall.c === cell.c) {
                return false;
            }
        }
        for (const snake of this.snakes) {
            let k = snake.cells.length;
            // 如果蛇尾会缩进，蛇尾不用判断
            if (!snake.check_tail_increasing()) {
                k--;
            }
            for (let i = 0; i < k; i++) {
                if (snake.cells[i].r === cell.r && snake.cells[i].c === cell.c)
                    return false;
            }
        }
        return true;
    }


    update() {
        this.update_size();
        if (this.check_ready()) {
            this.next_step();
        }
        this.render();
    }
    // 渲染
    render() {
        const color_even = "#AAD751", color_odd = "#A2D14F";
        for (let r = 0; r < this.rows; r++) {
            for (let c = 0; c < this.cols; c++) {
                if ((r + c) % 2 == 0) {
                    this.ctx.fillStyle = color_even;
                }
                else {
                    this.ctx.fillStyle = color_odd;
                }
                this.ctx.fillRect(this.L * c, this.L * r, this.L, this.L);
            }
        }

    }

}