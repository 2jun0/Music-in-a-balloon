const ConfettiContainer = () => {
  return (
    <canvas
      id="confetti-canvas"
      style={{
        position: 'fixed',
        width: '100%',
        height: '100%',
        top: '0',
        left: '0',
        zIndex: '1',
        pointerEvents: 'none',
      }}
    />
  );
};

export default ConfettiContainer;
