const GAME_OBJECTS = [];

export class GameObject {
    constructor() {
        GAME_OBJECTS.push(this);
        this.timedelta = 0; // 计算移动的帧
        this.has_called_started = false;
    }
    start() { // 只执行一次

    }

    update() { // 除了第一帧之外，每一帧执行一次

    }

    on_destroy() { // 删除之前执行

    }

    destroy() {
        this.on_destroy();
        for (let i in GAME_OBJECTS) {
            const obj = GAME_OBJECTS[i];
            if (obj == this) {
                // 销毁
                GAME_OBJECTS.splice(i);
                break;
            }
        }
    }
}
let last_timestamp; // 上一次执行的时刻
const step = () => {
    let timestamp;
    if (!last_timestamp) {
        last_timestamp = timestamp;
    }
    for (let obj of GAME_OBJECTS) {
        // 没有执行过
        if (!obj.has_called_started) {
            obj.has_called_started = true;
            obj.start();
        }
        // 更新
        else {
            timestamp = Date.now();
            obj.timedelta = timestamp - last_timestamp;
            obj.update();
        }
    }
    last_timestamp = timestamp;
    requestAnimationFrame(step)
}

requestAnimationFrame(step)