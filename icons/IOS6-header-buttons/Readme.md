The IOS header buttons. Probably this collection will grow in the future.

 - The plus button speaks for itself I think. It has a gadient in the border, which means that it can't be created in CSS
 - The back/normal buttons. This is sightly different. I actually think that in IOS, this button has a minimum and maximum width, but may grow within these boundaries. This can be done in CSS as well with using the button as background image, but so far I have not had the need for it yet; I'll just resize the button for whichever size I need.

CSS code. Note that it uses SVG background images which is not supported on all browsers. Further note that I'm only using bold text on retina displays. I'm sure that Apple actually uses another button on non-retina displays, but I don't feel like using that (and actually this works just fine for me)

        .pm-header {
            .plus-button {
                background-image: url(../img/plus@2x.svg); width: 33px; height: 30px; float: right; margin: 7px 10px;
                &:active {background-position-x: -33px;}
            }
            .back-button, .left-button, .right-button {
                @media (-webkit-min-device-pixel-ratio: 1.5) {font-weight: bold;} 
                color: #FFF; background-image: url(../img/header-button@2x.svg);
                margin: 7px 10px; font-size: 14px; font-weight: normal; text-decoration: none;
                width: 52px; padding-left: 12px; height: 26px; padding-top: 4px;
                &:active {background-position-x: -64px;}
                &.fake {background: none;} /*fake buttons can be used to center the header text */
            }
            .back-button, .left-button {float: left;}
            .back-button { background-position-y: 30px; }
            .right-button {float: right;}
       }

