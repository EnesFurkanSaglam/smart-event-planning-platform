import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import '../CSS/Message.css';

const Message = () => {
    const { id } = useParams();
    const [comments, setComments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [users, setUsers] = useState({});
    const [newComment, setNewComment] = useState("");
    const token = localStorage.getItem('accessToken');
    const [userData, setUserData] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem('accessToken');
        if (token) {
            fetch(`http://localhost:8081/user/users/me`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            })
                .then((response) => response.json())
                .then((data) => {
                    setUserData(data);
                    setLoading(false);
                    setError('');
                })
                .catch((error) => {
                    console.error('Error fetching user data:', error);
                    setError('Failed to fetch user data');
                    setLoading(false);
                });
        } else {
            setError('No token found');
            setLoading(false);
        }
    }, []);

    useEffect(() => {
        fetch(`http://localhost:8081/comment/comments/by-event/${id}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to fetch comments');
                }
                return response.json();
            })
            .then((data) => {
                setComments(data);
                setLoading(false);

                const userIds = [...new Set(data.map(comment => comment.messageSenderId))];
                userIds.forEach(userId => {
                    fetch(`http://localhost:8081/user/users/${userId}`, {
                        method: 'GET',
                        headers: {
                            'Authorization': `Bearer ${token}`,
                            'Content-Type': 'application/json',
                        },
                    })
                        .then((response) => {
                            if (!response.ok) {
                                throw new Error('Failed to fetch user');
                            }
                            return response.json();
                        })
                        .then((userData) => {
                            setUsers(prevUsers => ({
                                ...prevUsers,
                                [userId]: userData.name,
                            }));
                        })
                        .catch((err) => {
                            console.error('Error fetching user:', err);
                        });
                });
            })
            .catch((err) => {
                console.error('Error fetching comments:', err);
                setError(err.message || 'Failed to fetch comments');
                setLoading(false);
            });
    }, [id, token]);

    const handleCommentChange = (e) => {
        setNewComment(e.target.value);
    };

    const handleCommentSubmit = (e) => {
        e.preventDefault();

        const commentData = {
            eventId: id,
            messageSenderId: userData.userId,
            content: newComment,
        };

        fetch('http://localhost:8081/comment/comments', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(commentData),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to post comment');
                }
                return response.json();
            })
            .then(() => {
                setComments((prevComments) => [
                    ...prevComments,
                    { messageSenderId: userData.userId, content: newComment },
                ]);
                setNewComment("");
            })
            .catch((err) => {
                console.error('Error posting comment:', err);
                setError('Failed to post comment');
            });
    };

    if (loading) return <p>Loading comments...</p>;
    if (error) return <p>Error: {error}</p>;

    return (
        <div className="message-page-container">
            <h1>Messages Section</h1>
            {comments.length > 0 ? (
                <div className="comments-section">
                    {comments.map((comment) => (
                        <div key={comment.commentId} className="comment">
                            <p><strong>Sender:</strong> {users[comment.messageSenderId] || `User ${comment.messageSenderId}`}</p>
                            <p><strong>Message:</strong> {comment.content}</p>
                        </div>
                    ))}
                </div>
            ) : (
                <p>No comments yet.</p>
            )}

            <div className="comment-form">
                <textarea
                    value={newComment}
                    onChange={handleCommentChange}
                    placeholder="Type your message here..."
                    rows="4"
                    className="comment-textarea"
                    style={{ width: 400, marginLeft: 850, height: 30, }}
                />
                <button onClick={handleCommentSubmit} className="send-button" style={{ marginLeft: 20 }}>Send Message</button>
            </div>
        </div>
    );
};

export default Message;
