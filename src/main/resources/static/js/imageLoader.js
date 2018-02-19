"use strict";

var ImageLoader = (function () {

    function ImageLoader(elementEventId, inputFileId, outputElementId) {
        this.elementEvent = document.getElementById(elementEventId);
        this.inputFile = document.getElementById(inputFileId);
        this.outputElement = document.getElementById(outputElementId);
    }

    ImageLoader.prototype.init = function () {
        var _this = this;
        this.elementEvent.addEventListener('click', function (ev) {
            ev.preventDefault();
            _this.inputFile.addEventListener('change', function (e) {
                return _this._loadFile(e, _this.outputElement);
            });
            _this.inputFile.click();
            return false;
        });
    };
    ImageLoader.prototype._loadFile = function (event, output) {
        var reader;
        reader = new FileReader();
        reader.onload = function () {
            output.value = event.target.value;
            output.src = reader.result;
            output.style = null;
        };
        return reader.readAsDataURL(event.target.files[0]);
    };
    return ImageLoader;
}());