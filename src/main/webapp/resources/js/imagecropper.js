/*多尺寸 zc*/
(function (i) {
    var c = function (a, b, d, e) {
        this.width = a;
        this.height = b;
        this.cropWidth = d;
        this.cropHeight = e;
        this.imageContext = this.imageCanvas = this.image = null;
        this.imageScale = 1;
        this.imageViewTop = this.imageViewLeft = this.imageTop = this.imageLeft = this.imageRotation = 0;
        this.context = this.canvas = null;
        this.previews = [];
        this.maskGroup = [];
        this.maskAlpha = 0.4;
        this.maskColor = "#fff";
        this.cropTop = this.cropLeft = 0;
        this.cropViewWidth = d;
        this.cropViewHeight = e;
        this.dragSize = 7;
        this.dragColor = "#fff";
        this.mouseY = this.mouseX = this.dragTop =
        this.dragLeft = 0;
        this.isResizing = this.isMoving = this.inDragger = this.inCropper = false;
        this.cropStartHeight = this.cropStartWidth = this.cropStartTop = this.cropStartLeft = this.mouseStartY = this.mouseStartX = 0
    };
    i.ImageCropper = c;
    c.prototype.setCanvas = function (a) {
        this.canvas = document.getElementById(a) || a;
        this.context = this.canvas.getContext("2d");
        this.canvas.width = this.width;
        this.canvas.height = this.height;
        this.canvas.oncontextmenu = this.canvas.onselectstart = function () {
            return false
        };
        this.imageCanvas = document.createElement("canvas");
        this.imageContext = this.imageCanvas.getContext("2d");
        this.imageCanvas.width = this.width;
        this.imageCanvas.height = this.height
    };
    c.prototype.addPreview = function (a) {
        this.previews.push((document.getElementById(a) || a).getContext("2d"))
    };
    c.prototype.loadImage = function (a) {
        if (this.isAvaiable() && this.isImage(a)) {
            var b = new FileReader,
                d = this;
            b.readAsDataURL(a);
            b.onload = function (e) {
                if (!d.image) d.image = new Image;
                d.image.onload = function () {
                    d._init()
                };
                d.image.src = e.target.result
            }
        }
    };
    c.prototype._init = function () {
        var a =
        Math.min(this.width / this.image.width, this.height / this.image.height);
        if (a > 1) a = Math.min(this.cropViewWidth / this.image.width, this.cropViewHeight / this.image.height);
        if (this.image.width * a < this.cropViewWidth) a = Math.min(a, this.cropViewWidth / this.image.width);
        if (this.image.height * a < this.cropViewHeight) a = Math.min(a, this.cropViewHeight / this.image.height);
        this.imageViewLeft = this.imageLeft = (this.width - this.image.width * a) / 2;
        this.imageViewTop = this.imageTop = (this.height - this.image.height * a) / 2;
        this.imageScale = a;
        this.imageRotation = 0;
        a = Math.min(this.image.width * a, this.image.height * a);
        this.cropViewWidth = Math.min(a, this.cropWidth);
        this.cropViewHeight = Math.min(a, this.cropHeight);
        this.cropLeft = (this.width - this.cropViewWidth) / 2;
        this.cropTop = (this.height - this.cropViewHeight) / 2;
        this.dragLeft = this.cropLeft + this.cropViewWidth - this.dragSize / 2;
        this.dragTop = this.cropTop + this.cropViewHeight - this.dragSize / 2;
        this._update();
        var b = this;
        this.canvas.onmousedown = function (d) {
            b._mouseHandler.call(b, d)
        };
        this.canvas.onmouseup = function (d) {
            b._mouseHandler.call(b, d)
        };
        this.canvas.onmousemove = function (d) {
            b._mouseHandler.call(b, d)
        }
    };
    c.prototype._mouseHandler = function (a) {
        if (a.type == "mousemove") {
            var b = this.canvas.getClientRects()[0];
            this.mouseX = a.pageX - b.left - $(window).scrollLeft();
            this.mouseY = a.pageY - b.top - $(window).scrollTop();
            this._checkMouseBounds();
            this.canvas.style.cursor = this.inCropper || this.isMoving ? "move" : this.inDragger || this.isResizing ? "se-resize" : "";
            this.isMoving ? this._move() : this.isResizing && this._resize()
        } else if (a.type == "mousedown") {
            this.mouseStartX = this.mouseX;
            this.mouseStartY = this.mouseY;
            this.cropStartLeft = this.cropLeft;
            this.cropStartTop = this.cropTop;
            this.cropStartWidth = this.cropViewWidth;
            this.cropStartHeight = this.cropViewHeight;
            this.inCropper ? this.isMoving = true : this.inDragger && (this.isResizing = true)
        } else if (a.type == "mouseup") this.isMoving = this.isResizing = false
    };
    c.prototype._checkMouseBounds = function () {
        this.inCropper = this.mouseX >= this.cropLeft && this.mouseX <= this.cropLeft + this.cropViewWidth && this.mouseY >= this.cropTop && this.mouseY <= this.cropTop + this.cropViewHeight;
        this.inDragger = this.mouseX >= this.dragLeft && this.mouseX <= this.dragLeft + this.dragSize && this.mouseY >= this.dragTop && this.mouseY <= this.dragTop + this.dragSize;
        this.inCropper = this.inCropper && !this.inDragger
    };
    c.prototype._move = function () {
        var a = this.mouseY - this.mouseStartY;
        this.cropLeft = Math.max(this.imageViewLeft, this.cropStartLeft + (this.mouseX - this.mouseStartX));
        this.cropLeft = Math.min(this.cropLeft, this.width - this.imageViewLeft - this.cropViewWidth);
        this.cropTop = Math.max(this.imageViewTop, this.cropStartTop + a);
        this.cropTop = Math.min(this.cropTop, this.height - this.imageViewTop - this.cropViewHeight);
        this.dragLeft = this.cropLeft + this.cropViewWidth - this.dragSize / 2;
        this.dragTop = this.cropTop + this.cropViewHeight - this.dragSize / 2;
        this._update()
    };
    c.prototype._resize = function () {
        var a = Math.min(this.mouseX - this.mouseStartX, this.mouseY - this.mouseStartY),
            b = Math.max(10, this.cropStartWidth + a);
        a = Math.max(10, this.cropStartHeight + a);
        b = Math.min(b, this.width - this.cropStartLeft - this.imageViewLeft);
        a = Math.min(a, this.height - this.cropStartTop - this.imageViewTop);
        this.cropViewWidth =
        this.cropViewHeight = Math.round(Math.min(b, a));
        this.dragLeft = this.cropLeft + this.cropViewWidth - this.dragSize / 2;
        this.dragTop = this.cropTop + this.cropViewHeight - this.dragSize / 2;
        this._update()
    };
    c.prototype.rotate = function (a) {
        if (this.image) {
            this.imageRotation += a;
            this.imageViewLeft = (a = Math.abs(this.imageRotation % 180) == 90) ? this.imageTop : this.imageLeft;
            this.imageViewTop = a ? this.imageLeft : this.imageTop;
            this.cropLeft = (this.width - this.cropViewWidth) / 2;
            this.cropTop = (this.height - this.cropViewHeight) / 2;
            this.dragLeft =
            this.cropLeft + this.cropViewWidth - this.dragSize / 2;
            this.dragTop = this.cropTop + this.cropViewHeight - this.dragSize / 2;
            this._update()
        }
    };
    c.prototype._update = function () {
        this.context.clearRect(0, 0, this.width, this.height);
        this._drawImage();
        this._drawMask();
        this._drawDragger();
        this._drawPreview()
    };
    c.prototype._drawImage = function () {
        this.imageContext.clearRect(0, 0, this.width, this.height);
        this.imageContext.save();
        var a = this.imageRotation % 360;
        this.imageContext.translate(this.imageViewLeft, this.imageViewTop);
        this.imageContext.scale(this.imageScale, this.imageScale);
        this.imageContext.rotate(this.imageRotation * Math.PI / 180);
        switch ((360 - a) % 360) {
        case 90:
            this.imageContext.drawImage(this.image, -this.image.width, 0);
            break;
        case 180:
            this.imageContext.drawImage(this.image, -this.image.width, -this.image.height);
            break;
        case 270:
            this.imageContext.drawImage(this.image, 0, -this.image.height);
            break;
        default:
            this.imageContext.drawImage(this.image, 0, 0)
        }
        this.imageContext.restore();
        this.context.drawImage(this.imageCanvas, 0, 0)
    };
    c.prototype._drawPreview = function () {
        for (var a =
        0; a < this.previews.length; a++) {
            var b = this.previews[a];
            b.clearRect(0, 0, b.canvas.width, b.canvas.height);
            b.save();
            b.drawImage(this.imageCanvas, this.cropLeft, this.cropTop, this.cropViewWidth, this.cropViewHeight, 0, 0, b.canvas.width, b.canvas.height);
            b.restore()
        }
    };
    c.prototype._drawMask = function () {
        this._drawRect(this.imageViewLeft, this.imageViewTop, this.cropLeft - this.imageViewLeft, this.height, this.maskColor, null, this.maskAlpha);
        this._drawRect(this.cropLeft + this.cropViewWidth, this.imageViewTop, this.width - this.cropViewWidth - this.cropLeft + this.imageViewLeft, this.height, this.maskColor, null, this.maskAlpha);
        this._drawRect(this.cropLeft, this.imageViewTop, this.cropViewWidth, this.cropTop - this.imageViewTop, this.maskColor, null, this.maskAlpha);
        this._drawRect(this.cropLeft, this.cropTop + this.cropViewHeight, this.cropViewWidth, this.height - this.cropViewHeight - this.cropTop + this.imageViewTop, this.maskColor, null, this.maskAlpha)
    };
    c.prototype._drawDragger = function () {
        this._drawRect(this.dragLeft, this.dragTop, this.dragSize, this.dragSize, null, this.dragColor, null)
    };
    c.prototype._drawRect = function (a, b, d, e, f, g, h) {
        this.context.save();
        if (f !== null) this.context.fillStyle = f;
        if (g !== null) this.context.strokeStyle = g;
        if (h !== null) this.context.globalAlpha = h;
        this.context.beginPath();
        this.context.rect(a, b, d, e);
        this.context.closePath();
        f !== null && this.context.fill();
        g !== null && this.context.stroke();
        this.context.restore()
    };
    c.prototype.getCroppedImageData = function (a, b, d) {
        var e = document.createElement("canvas");
        e.width = a || this.cropWidth;
        e.height = b || this.cropHeight;
        e.getContext("2d").drawImage(this.imageCanvas, this.cropLeft, this.cropTop, this.cropViewWidth, this.cropViewHeight, 0, 0, e.width, e.height);
        return e.toDataURL(d || "image/jpeg")
    };
    c.prototype.isAvaiable = function () {
        return typeof FileReader !== "undefined"
    };
    c.prototype.isImage = function (a) {
        return /image/i.test(a.type)
    }
})(window);