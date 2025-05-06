import React, { useEffect, useRef, useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import * as actions from "../../store/actions";
import { CiUser, CiHeart } from "react-icons/ci";
import { Button, Modal } from 'react-bootstrap';
import './Header.scss';
import Transaction from '../Transaction/Transaction';
import store from '../../redux';
import axios from 'axios';

import { handleCryptoPayment } from '../../utils/cryptoPayment';


const YOUR_RECEIVER_WALLET = "0x14fe232b9B32b1e593fa29b60273D9E21782142a";

const Header = (props) => {
    const { isScrollTop } = props;
    const dispatch = useDispatch();
    const location = useLocation();
    const menuRef = useRef(null);
    const isLogin = useSelector(state => state.user.isLoggedIn);
    const favourites = useSelector(state => state.user.favourites);
    const userName = useSelector(state => state.user.userName);

    const [show, setShow] = useState(false);
    const [showMembership, setShowMembership] = useState(false);
    const [highlightStyle, setHighlightStyle] = useState({});

    // Function to handle logout
    const handleLogout = () => {
        const isConfirmed = window.confirm("Bạn có chắc muốn đăng xuất khỏi hệ thống không?");
        if (isConfirmed) {
            dispatch(actions.process_Logout());
        }
    };

    // Function to move highlight when navigating menu
    const moveHighlight = (option) => {
        const optionRect = option.getBoundingClientRect();
        const menuRect = menuRef.current.getBoundingClientRect();
        const leftPosition = optionRect.left - menuRect.left;

        setHighlightStyle({
            width: `${optionRect.width}px`,
            left: `${leftPosition}px`,
        });
    };

    // Update menu highlight on location change
    useEffect(() => {
        const updateHighlight = () => {
            let arrActive = [];
            document.querySelectorAll('.menu-option').forEach(item => {
                const li = item.querySelector('li');
                li.classList.remove('active');
                if (item.pathname + item.hash === location.pathname + location.hash) {
                    li.classList.add('active');
                    arrActive.push(li);
                    moveHighlight(li);
                }
            });
            if (arrActive.length === 0) {
                setHighlightStyle({ left: `100%` });
            }
        };

        window.addEventListener('resize', updateHighlight);
        updateHighlight();

        return () => {
            window.removeEventListener('resize', updateHighlight);
        };
    }, [location]);

    // Handle modal show/hide
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const handleShowMembership = () => setShowMembership(true);
    const handleCloseMembership = () => setShowMembership(false);

    const handleContactAfterPayment = async (transactionHash) => {
        const localData = JSON.parse(localStorage.getItem('persist:user'));
        const jwtToken = localData && localData.jwtToken ?
            (localData.jwtToken.replace(/"/g, '')
                ? localData.jwtToken.replace(/"/g, '')
                : store.getState().user.jwtToken)
            : store.getState().user.jwtToken;

        try {
            const data = {
                buildingId: 19,         // mặc định là 19
                note: transactionHash   // ghi chú là mã giao dịch blockchain
            };
            const response = await axios.post('http://localhost:8081/customer',
                data,
                {
                    headers: {
                        'Authorization': jwtToken,
                    }
                });
            if (response.data.statusCode) {
                alert("Liên hệ thành công sau khi thanh toán.");
            } else {
                alert("Liên hệ thất bại sau khi thanh toán.");
            }
        } catch (err) {
            console.error("Lỗi khi gửi thông tin sau thanh toán:", err);
        }
    };

    // Handle the crypto payment
    const handleClickPayment = () => {
        handleCryptoPayment({
            receiverWallet: YOUR_RECEIVER_WALLET,
            amountInEth: "0.0005",
            onSuccess: (tx) => {
                alert(`Thanh toán thành công! Mã giao dịch: ${tx.hash}`);
                handleContactAfterPayment(tx.hash); // Gọi API sau khi thanh toán thành công
                handleCloseMembership(); // Đóng modal đăng ký hội viên
            },
            onError: (err) => {
                alert("Thanh toán thất bại hoặc bị từ chối.");
            }
        });
    };


    return (
        <div className='header-container'>
            {/* Modal liên hệ */}
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Các bất động sản bạn đã liên hệ với chúng tôi</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Transaction handleClose={handleClose} />
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Đóng
                    </Button>
                </Modal.Footer>
            </Modal>

            {/* Modal đăng ký hội viên */}
            <Modal show={showMembership} onHide={handleCloseMembership}>
                <Modal.Header closeButton>
                    <Modal.Title>Đăng ký hội viên</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <p>Chào bạn, hãy đăng ký hội viên để tận hưởng các quyền lợi đặc biệt!</p>
                    <p><strong>Giá: 0.0005 SepoliaETH </strong></p>
                    <Button variant="success" onClick={handleClickPayment}>
                        Thanh toán và đăng ký
                    </Button>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseMembership}>
                        Hủy
                    </Button>
                </Modal.Footer>
            </Modal>

            <div className={isScrollTop ? 'header_content scrollTop' : 'header_content unScrollTop'}>
                <div className='left-content col-2'>
                    <div className='header_logo'>
                        <Link to='/home' className='image_logo'></Link>
                        <div className='name_page'>
                            <p>Vungmoigioi</p>
                            <span>Start a new life</span>
                        </div>
                    </div>
                </div>

                <div className='center-content col-5'>
                    <div className='layout-not-phone'>
                        <ul className='container' ref={menuRef}>
                            <div className="highlight" style={highlightStyle}></div>
                            <Link to='/home' className="menu-option">
                                <li>Trang chủ</li>
                            </Link>
                            <Link to='/product' className="menu-option">
                                <li>Dự án</li>
                            </Link>
                            <Link to='/news' className="menu-option">
                                <li>Tin tức</li>
                            </Link>
                            <Link to='/home#about' className="menu-option">
                                <li>Giới thiệu</li>
                            </Link>
                        </ul>
                    </div>
                </div>

                <div className='right-content col-5'>
                    <Link to='/favourite'>
                        <div className='favourite'>
                            <CiHeart className={JSON.parse(favourites).length > 0 ? 'icon_heart yes' : 'icon_heart'} />
                            <sup>{JSON.parse(favourites).length > 0 && JSON.parse(favourites).length}</sup>
                        </div>
                    </Link>

                    {isLogin ? (
                        <>
                            <div className='profile' onClick={handleShow}>
                                <CiUser className='icon_profile' />
                                <p>{userName}</p>
                            </div>
                            <div className='member-register'>
                                <button className='btn-member' onClick={handleShowMembership}>
                                    Đăng ký hội viên
                                </button>

                            </div>
                            <div className='logout'>
                                <button onClick={handleLogout} className='btn-logout'>
                                    Đăng xuất
                                </button>
                            </div>
                        </>
                    ) : (
                        <>
                            <div className='login'>
                                <Link to='/login'>
                                    <button className='btn-login'>Đăng nhập</button>
                                </Link>
                            </div>
                            <div className='register'>
                                <Link to='/register'>
                                    <button className='btn-register'>Đăng ký</button>
                                </Link>
                            </div>
                        </>
                    )}
                </div>
            </div>
        </div>
    );
};

export default Header;
