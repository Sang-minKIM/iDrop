import React, { useState, useEffect } from "react";
import ReactDOM from "react-dom";
import styles from "./Modal.module.scss"; // CSS 모듈 import
import { Footer } from "../Common/Footer/Footer";
import { AddressFinderMap } from "./AddressFinderMap/AddressFinderMap";

const Modal = ({ isOpen, onClose }) => {
    const [animate, setAnimate] = useState(false);
    const [newAddress, setNewAddress] = useState("");
    const handleClose = () => {
        setAnimate(false); // 모달 닫기 애니메이션 시작
    };

    const handleAnimationEnd = () => {
        if (!animate) {
            onClose(); // 애니메이션 종료 후 모달 닫기
        }
    };

    useEffect(() => {
        if (isOpen) {
            setAnimate(true); // 모달 열기 애니메이션 시작
        }
    }, [isOpen]);

    if (!isOpen && !animate) return null;

    return ReactDOM.createPortal(
        <div
            className={`${styles.modalFull} ${
                animate ? styles.slideIn : styles.slideOut
            }`}
            onAnimationEnd={handleAnimationEnd}
        >
            <AddressFinderMap
                newAddress={newAddress}
                setNewAddress={setNewAddress}
            />
            <div className={styles.address}>{newAddress}</div>
            <Footer
                className={styles.closeButton}
                onClick={handleClose}
                text="완료"
            />
        </div>,
        document.getElementById("portal")
    );
};

export default Modal;
