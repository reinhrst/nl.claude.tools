The IOS header buttons. Probably this collection will grow in the future.

 - The plus button speaks for itself I think. It has a gadient in the border, which means that it can't be created in CSS
 - The back button. This is sightly different. I actually think that in IOS, this button has a minimum and maximum width, but may grow within these boundaries. This can be done in CSS as well with using the button as background image, but so far I have not had the need for it yet; I'll just resize the button for whichever size I need.

CSS code. Note that it uses SVG background images which is not supported on all browsers. Further note that I'm only using bold text on retina displays. I'm sure that Apple actually uses another button on non-retina displays, but I don't feel like using that (and actually this works just fine for me)

        .plus-button {
            background-image: url(../img/plus@2x.svg);
            width: 33px;
            height: 30px;
            float: right;
            margin: 7px 10px;
            &:active {background-image-position: -33px;}
        }
        .back-button {
            @media (-webkit-min-device-pixel-ratio: 1.5) {font-weight: bold;} 
            color: #FFF;
            background-image: url(../img/back@2x.svg);
            width: 52px; padding-left: 12px;
            height: 26px; padding-top: 4px;
            float: left;
            margin: 7px 10px;
            font-size: 14px; font-weight: normal; text-decoration: none;
            &:active {background-image-position: -64px;}
        }

